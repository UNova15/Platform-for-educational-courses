package progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

public class TestAttemptNotFoundException extends RuntimeException {
    public TestAttemptNotFoundException(Id<User> userId, Id<Test> testId) {
        super("Not found test attempt with test ID: %s for user with ID: %s".formatted(testId.value(), userId.value()));
    }
}
