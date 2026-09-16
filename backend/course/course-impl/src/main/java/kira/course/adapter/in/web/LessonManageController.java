package kira.course.adapter.in.web;

import common.domain.Id;
import jakarta.validation.Valid;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import kira.infrastructure.api.TokenPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import kira.course.application.port.in.lesson.create.LessonCreateCommand;
import kira.course.application.port.in.lesson.create.LessonCreateResult;
import kira.course.application.port.in.lesson.create.LessonCreateUseCase;
import kira.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import kira.course.application.port.in.lesson.update.LessonUpdateCommand;
import kira.course.application.port.in.lesson.update.LessonUpdateUseCase;

@RestController
@RequiredArgsConstructor
public class LessonManageController {
    private final LessonCreateUseCase createUseCase;
    private final LessonUpdateUseCase updateUseCase;
    private final LessonRemoveUseCase removeUseCase;

    @PostMapping("/modules/{moduleId}/lessons")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.CREATED)
    public LessonCreateResult createLesson(
            @Valid @RequestBody LessonCreateCommand createCommand,
            @PathVariable("moduleId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return createUseCase.createLesson(createCommand, userId, moduleId);
    }

    @DeleteMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLesson(@PathVariable("lessonId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        removeUseCase.removeLesson(userId, lessonId);
    }

    @PutMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLesson(
            @Valid @RequestBody LessonUpdateCommand command,
            @PathVariable("lessonId") Long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        updateUseCase.updateLesson(command, lessonId, userId);
    }
}
