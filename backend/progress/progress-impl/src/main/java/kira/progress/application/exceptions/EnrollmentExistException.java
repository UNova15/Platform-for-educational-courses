package kira.progress.application.exceptions;

import common.domain.Id;
import common.exception.ResourceAlreadyExistException;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;

public class EnrollmentExistException extends ResourceAlreadyExistException {

    public EnrollmentExistException(Id<User> userId, Id<Course> courseId) {
        super(
                ProgressExceptionCode.ENROLLMENT_ALREADY_EXIST_EXCEPTION,
                "User with ID: %s already have enrollment on course with ID: %s "
                        .formatted(userId.value(), courseId.value()));
        setProperty("userId", userId.value());
        setProperty("courseId", courseId.value());
    }
}
