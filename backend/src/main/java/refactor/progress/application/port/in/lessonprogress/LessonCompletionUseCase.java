package refactor.progress.application.port.in.lessonprogress;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.User;

@Validated
public interface LessonCompletionUseCase {
    void completeLesson(@NotNull Id<User> userId, @NotNull Id<Lesson> lessonId);
}
