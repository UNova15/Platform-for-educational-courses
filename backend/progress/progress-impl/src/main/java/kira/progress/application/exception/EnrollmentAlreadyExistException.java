package kira.progress.application.exception;

import common.domain.entity.Id;
import common.application.exception.ResourceAlreadyExist;
import kira.progress.domain.ProgressAggregateType;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;

public class EnrollmentAlreadyExistException extends ResourceAlreadyExist {

    public EnrollmentAlreadyExistException(Id<User> userId, Id<Course> courseId) {
        super(ProgressAggregateType.ENROLLMENT);
        withContext("userId", userId.value());
        withContext("courseId", courseId.value());
    }
}
