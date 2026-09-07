package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.user.Account;

public class CourseNotFoundException extends EntityNotFoundException {
    public CourseNotFoundException(Id<Course> courseId, Id<Account> teacherId) {
        super("Not found course with id: %s and teacher id: %s".formatted(courseId.value(), teacherId.value()));
    }
}
