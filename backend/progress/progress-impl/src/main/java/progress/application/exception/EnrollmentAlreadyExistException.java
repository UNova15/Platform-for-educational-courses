package progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.User;

public class EnrollmentAlreadyExistException extends RuntimeException {
    public EnrollmentAlreadyExistException(Id<User> userId, Id<Course> courseId) {
        super("Enrollment with course ID: %s and user ID: %s already exist"
                .formatted(courseId.value(), userId.value()));
    }
}
