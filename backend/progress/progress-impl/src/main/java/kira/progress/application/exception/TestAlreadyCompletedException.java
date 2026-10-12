package kira.progress.application.exception;

import common.domain.Id;
import common.exception.core.RuleConflictException;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public class TestAlreadyCompletedException extends RuleConflictException {

    public TestAlreadyCompletedException(Id<Test> testId, Id<User> userId) {
        super("Test attempt already completed", ProgressResourceName.ATTEMPT, testId);
        withContext("userId", userId.value());
    }
}
