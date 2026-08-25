package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.internal.lesson.Lesson;

public class LessonNotFoundException extends NotFoundException {
    public LessonNotFoundException(Id<Lesson> lessonId) {
        super("Not found lesson with ID: %d".formatted(lessonId.value()));
    }
}
