package refactor.progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.User;

public class LessonAccessDenyException extends RuntimeException {
    public LessonAccessDenyException(Id<User> userId, Id<Lesson> lessonId) {
        super("User with ID: %s does not have access to lesson with ID: %s"
                .formatted(userId.value(), lessonId.value()));
    }
}
