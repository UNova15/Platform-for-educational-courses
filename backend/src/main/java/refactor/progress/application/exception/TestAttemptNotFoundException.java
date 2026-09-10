package refactor.progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

public class TestAttemptNotFoundException extends RuntimeException {
    public TestAttemptNotFoundException(Id<User> userId, Id<Test> testId) {
        super("Not found test attempt with test ID: %s for user with ID: %s".formatted(testId.value(), userId.value()));
    }
}
