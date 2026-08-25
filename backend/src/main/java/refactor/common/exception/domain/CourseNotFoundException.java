package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException(Id<Course> courseId, Id<User> teacherId) {
        super("Not found course with id: %s and teacher id: %s".formatted(courseId.value(), teacherId.value()));
    }
}
