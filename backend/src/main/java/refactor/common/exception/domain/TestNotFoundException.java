package refactor.common.exception.domain;

public class TestNotFoundException extends NotFoundException {
    public TestNotFoundException(long testId) {
        super("Test with ID: %d not found".formatted(testId));
    }
}
