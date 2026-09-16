package kira.course.application.exceptions;

import common.domain.Id;
import common.exception.BaseApplicationException;
import kira.course.domain.markers.User;
import kira.course.domain.test.Test;

import java.util.Map;

public class TestNotStartedException extends BaseApplicationException {

    public TestNotStartedException(Id<Test> testId, Id<User> userId) {
        super(
                CourseExceptionCode.TEST_NOT_STARTED_EXCEPTION,
                "Test with ID: %s not started with user ID: %s".formatted(testId.value(), userId.value()),
                Map.of("testId", testId.value(), "userId", userId.value()));
    }
}
