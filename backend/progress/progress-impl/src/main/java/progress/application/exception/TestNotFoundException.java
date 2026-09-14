package progress.application.exception;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Test;

public class TestNotFoundException extends RuntimeException {
    public TestNotFoundException(Id<Test> testId) {
        super("Test with ID: %s not found".formatted(testId.value()));
    }
}
