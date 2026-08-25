package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.internal.test.Test;

public class TestNotFoundException extends NotFoundException {
    public TestNotFoundException(Id<Test> testId) {
        super("Test with ID: %d not found".formatted(testId.value()));
    }
}
