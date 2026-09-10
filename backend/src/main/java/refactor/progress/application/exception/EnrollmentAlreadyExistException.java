package refactor.progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.User;

public class EnrollmentAlreadyExistException extends RuntimeException {
    public EnrollmentAlreadyExistException(Id<User> userId, Id<Course> courseId) {
        super("Enrollment with course ID: %s and user ID: %s already exist"
                .formatted(courseId.value(), userId.value()));
    }
}
