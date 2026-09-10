package refactor.course.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.common.domain.Id;
import refactor.course.application.port.in.lesson.create.LessonCreateCommand;
import refactor.course.application.port.in.lesson.create.LessonCreateResult;
import refactor.course.application.port.in.lesson.create.LessonCreateUseCase;
import refactor.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import refactor.course.application.port.in.lesson.update.LessonUpdateCommand;
import refactor.course.application.port.in.lesson.update.LessonUpdateUseCase;
import refactor.course.domain.markers.Account;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;
import refactor.infrastructure.accesstoken.TokenPayload;

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
            @PathVariable("moduleId") long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<CourseModule> moduleId = Id.of(resourceId);

        return createUseCase.createLesson(createCommand, userId, moduleId);
    }

    @DeleteMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLesson(@PathVariable("lessonId") long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        removeUseCase.removeLesson(userId, lessonId);
    }

    @PutMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLesson(
            @Valid @RequestBody LessonUpdateCommand command,
            @PathVariable("lessonId") long resourceId,
            @AuthenticationPrincipal TokenPayload token) {
        Id<Account> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        updateUseCase.updateLesson(command, lessonId, userId);
    }
}
