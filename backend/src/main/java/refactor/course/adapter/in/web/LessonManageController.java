package refactor.course.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import refactor.course.application.port.in.lesson.create.LessonCreateCommand;
import refactor.course.application.port.in.lesson.create.LessonCreateResult;
import refactor.course.application.port.in.lesson.create.LessonCreateUseCase;
import refactor.course.application.port.in.lesson.remove.LessonRemoveUseCase;
import refactor.course.application.port.in.lesson.update.LessonUpdateCommand;
import refactor.course.application.port.in.lesson.update.LessonUpdateUseCase;
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
            @PathVariable long moduleId,
            @AuthenticationPrincipal TokenPayload token) {
        return createUseCase.createLesson(createCommand, token.userId(), moduleId);
    }

    @DeleteMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLesson(@PathVariable long lessonId, @AuthenticationPrincipal TokenPayload token) {
        removeUseCase.removeLesson(token.userId(), lessonId);
    }

    @PutMapping("/lessons/{lessonId}")
    @PreAuthorize("hasRole('TEACHER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLesson(
            @Valid @RequestBody LessonUpdateCommand command,
            @PathVariable long lessonId,
            @AuthenticationPrincipal TokenPayload token) {
        updateUseCase.updateLesson(command, lessonId, token.userId());
    }
}
