package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;

public class CourseAccessException extends AccessException {

    public CourseAccessException(Id<Course> courseId, Id<User> requesterId) {
        super(courseId.value(), requesterId.value());
    }
}
