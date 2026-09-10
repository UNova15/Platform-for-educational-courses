package refactor.progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.User;

public class LessonAlreadyCompletedException extends RuntimeException {
    public LessonAlreadyCompletedException(Id<Lesson> lessonId, Id<User> userId) {
        super("User with ID: %s already completed lesson with ID: %s".formatted(userId.value(), lessonId.value()));
    }
}
