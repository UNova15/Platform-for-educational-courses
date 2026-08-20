package refactor.common.exception.domain;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException(long courseId, long teacherId) {
        super("Not found course with id: %s and teacher id: %s".formatted(courseId, teacherId));
    }
}
