package kira.progress.adapter.in.web;

import common.domain.Id;
import kira.infrastructure.api.TokenPayload;
import kira.progress.application.port.in.lessonprogress.LessonCompletionUseCase;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LessonProgressController {
    private final LessonCompletionUseCase lessonCompletionUseCase;

    @PostMapping("/lessons/{lessonId}/progress")
    @ResponseStatus(HttpStatus.OK)
    public void completeLesson(@PathVariable("lessonId") Long resourceId, @AuthenticationPrincipal TokenPayload token) {
        Id<User> userId = Id.of(token.userId());
        Id<Lesson> lessonId = Id.of(resourceId);

        lessonCompletionUseCase.completeLesson(userId, lessonId);
    }
}
