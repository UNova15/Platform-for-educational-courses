package kira.progress.application.exception;

import common.application.exception.RuleConflictException;
import common.domain.entity.Id;
import kira.progress.domain.ProgressAggregateType;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAttempt;

public class TestAlreadyCompletedException extends RuleConflictException {

    public TestAlreadyCompletedException(Id<TestAttempt> attemptId, Id<Test> testId, Id<User> userId) {
        super("Test attempt already completed", ProgressAggregateType.ATTEMPT, testId);
        withContext("userId", userId.value());
        withContext("attemptId", attemptId.value());
    }
}
