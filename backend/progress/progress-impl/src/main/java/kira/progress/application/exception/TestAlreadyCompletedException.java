package kira.progress.application.exception;

import common.domain.entity.Id;
import common.application.exception.RuleConflictException;
import kira.progress.domain.ProgressAggregateType;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;

public class TestAlreadyCompletedException extends RuleConflictException {

    public TestAlreadyCompletedException(Id<Test> testId, Id<User> userId) {
        super("Test attempt already completed", ProgressAggregateType.ATTEMPT, testId);
        withContext("userId", userId.value());
    }
}
