package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.user.Account;

public class CourseAccessException extends AccessException {

    public CourseAccessException(Id<Course> courseId, Id<Account> requesterId) {
        super(courseId.value(), requesterId.value());
    }
}
