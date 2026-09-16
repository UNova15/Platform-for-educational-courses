package kira.progress.application.exceptions;

import common.domain.Id;
import common.exception.BaseApplicationException;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public class TestAlreadyCompletedException extends BaseApplicationException {
    private static final String TEMPLATE = "User with ID: %s already completed test with ID: %s";

    public TestAlreadyCompletedException(Id<Test> testId, Id<User> userId) {
        super(
                ProgressExceptionCode.TEST_ALREADY_COMPLETED_EXCEPTION,
                TEMPLATE.formatted(userId.value(), testId.value()));
        setProperty("userId", userId.value());
        setProperty("testId", testId.value());
    }
}
