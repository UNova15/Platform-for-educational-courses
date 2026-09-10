package refactor.progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Test;
import refactor.progress.domain.markers.User;

public class TestAccessDenyException extends RuntimeException {
    public TestAccessDenyException(Id<User> userId, Id<Test> testId) {
        super("User with ID %s does not have access to test with ID %s".formatted(userId.value(), testId.value()));
    }
}
