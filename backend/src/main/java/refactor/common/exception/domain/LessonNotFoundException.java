package refactor.common.exception.domain;

public class LessonNotFoundException extends NotFoundException {
    public LessonNotFoundException(long lessonId) {
        super("Not found lesson with ID: %d".formatted(lessonId));
    }
}
