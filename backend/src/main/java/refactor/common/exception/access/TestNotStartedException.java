package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.markers.Account;
import refactor.course.domain.test.Test;

public class TestNotStartedException extends RuntimeException {
    public TestNotStartedException(Id<Account> requesterId, Id<Test> testId) {
        super("User with ID %s does not completed test with ID %s".formatted(requesterId.value(), testId.value()));
    }
}
