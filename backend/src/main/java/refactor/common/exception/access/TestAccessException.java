package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.user.Account;
import refactor.course.domain.test.Test;

public class TestAccessException extends AccessException {
    public TestAccessException(Id<Test> testId, Id<Account> requesterId) {
        super(testId.value(), requesterId.value());
    }
}
