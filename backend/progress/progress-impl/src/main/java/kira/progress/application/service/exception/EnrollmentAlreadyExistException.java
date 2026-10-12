package kira.progress.application.service.exception;

import common.domain.Id;
import common.exception.named.ResourceAlreadyExist;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;

public class EnrollmentAlreadyExistException extends ResourceAlreadyExist {

    public EnrollmentAlreadyExistException(Id<User> userId, Id<Course> courseId) {
        super(ProgressResourceName.ENROLLMENT);
        withContext("userId", userId.value());
        withContext("courseId", courseId.value());
    }
}
