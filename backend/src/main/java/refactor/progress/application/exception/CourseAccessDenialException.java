package refactor.progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.User;

public class CourseAccessDenialException extends RuntimeException {
    public CourseAccessDenialException(Id<Course> courseId, Id<User> userId) {
        super("User with ID: %s does not have access to the course with ID: %s"
                .formatted(userId.value(), courseId.value()));
    }
}
