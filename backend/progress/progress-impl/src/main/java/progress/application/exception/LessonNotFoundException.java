package progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Lesson;

public class LessonNotFoundException extends RuntimeException {
    public LessonNotFoundException(Id<Lesson> lessonId) {
        super("Lesson with ID: %s not found".formatted(lessonId.value()));
    }
}
