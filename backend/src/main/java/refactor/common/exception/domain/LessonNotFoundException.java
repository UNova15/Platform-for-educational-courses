package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.lesson.Lesson;

public class LessonNotFoundException extends EntityNotFoundException {
    public LessonNotFoundException(Id<Lesson> lessonId) {
        super("Not found lesson with ID: %d".formatted(lessonId.value()));
    }
}
