package kira.progress.application.port.in.lessonprogress;

import common.domain.Id;
import jakarta.validation.constraints.NotNull;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonCompletionUseCase {
    void completeLesson(@NotNull Id<User> userId, @NotNull Id<Lesson> lessonId);
}
