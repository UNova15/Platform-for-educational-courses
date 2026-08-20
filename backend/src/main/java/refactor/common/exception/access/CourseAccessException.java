package refactor.common.exception.access;

public class CourseAccessException extends AccessException {

    public CourseAccessException(long courseId, long requesterId) {
        super(courseId, requesterId);
    }
}
