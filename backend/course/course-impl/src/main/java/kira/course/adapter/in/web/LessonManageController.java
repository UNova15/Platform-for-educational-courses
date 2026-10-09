package kira.course.adapter.in.web;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import kira.course.application.port.in.lesson.create.LessonCreateCommand;
import kira.course.application.port.in.lesson.create.LessonCreateResponse;
import kira.course.application.port.in.lesson.create.LessonCreateUseCase;
import kira.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import kira.course.application.port.in.lesson.update.LessonUpdateCommand;
import kira.course.application.port.in.lesson.update.LessonUpdateUseCase;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LessonManageController {
    private final LessonCreateUseCase createUseCase;
    private final LessonUpdateUseCase updateUseCase;
    private final LessonRemoveUseCase removeUseCase;

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<?> createLesson(
            @Valid @RequestBody LessonCreateCommand createCommand,
            @PathVariable("moduleId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        Result<LessonCreateResponse, LessonCreateUseCase.CreateErrors> result =
                createUseCase.createLesson(createCommand, userId, moduleId);

        return result.fold(ResponseEntity::ok, error -> switch (error) {
            case COURSE_ARCHIVED -> ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            case MODULE_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
        });
    }

    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<?> removeLesson(
            @PathVariable("lessonId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        Result<Void, LessonRemoveUseCase.RemoveErrors> result = removeUseCase.removeLesson(userId, lessonId);

        return result.fold(success -> ResponseEntity.ok().build(), error -> switch (error) {
            case LESSON_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case COURSE_ARCHIVED -> ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }

    @PutMapping("/lessons/{lessonId}")
    public ResponseEntity<?> updateLesson(
            @Valid @RequestBody LessonUpdateCommand command,
            @PathVariable("lessonId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        Result<Void, LessonUpdateUseCase.UpdateError> result = updateUseCase.updateLesson(command, lessonId, userId);

        return result.fold(success -> ResponseEntity.ok().build(), error -> switch (error) {
            case ACCESS_DENY -> ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
            case LESSON_NOT_FOUND -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            case COURSE_ARCHIVED -> ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        });
    }
}
