package progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;
import refactor.progress.implementation.domain.markers.User;

public class TestAccessDenyException extends RuntimeException {
    public TestAccessDenyException(Id<User> userId, Id<Test> testId) {
        super("User with ID %s does not have access to test with ID %s".formatted(userId.value(), testId.value()));
    }
}
