package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.external.User;
import refactor.course.domain.internal.test.Test;

public class TestAccessException extends AccessException {
    public TestAccessException(Id<Test> testId, Id<User> requesterId) {
        super(testId.value(), requesterId.value());
    }
}
