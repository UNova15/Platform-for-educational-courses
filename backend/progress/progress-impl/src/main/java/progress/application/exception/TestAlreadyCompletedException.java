package progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

public class TestAlreadyCompletedException extends RuntimeException {
    public TestAlreadyCompletedException(Id<User> userId, Id<Test> testId) {
        super("User with ID %s already passed test with ID %s".formatted(userId.value(), testId.value()));
    }
}
