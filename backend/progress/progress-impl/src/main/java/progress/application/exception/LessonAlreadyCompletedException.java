package progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.User;

public class LessonAlreadyCompletedException extends RuntimeException {
    public LessonAlreadyCompletedException(Id<Lesson> lessonId, Id<User> userId) {
        super("User with ID: %s already completed lesson with ID: %s".formatted(userId.value(), lessonId.value()));
    }
}
