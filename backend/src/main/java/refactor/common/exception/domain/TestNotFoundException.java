package refactor.common.exception.domain;

import refactor.common.domain.Id;
import refactor.course.domain.test.Test;

public class TestNotFoundException extends EntityNotFoundException {
    public TestNotFoundException(Id<Test> testId) {
        super("Test with ID: %d not found".formatted(testId.value()));
    }
}
