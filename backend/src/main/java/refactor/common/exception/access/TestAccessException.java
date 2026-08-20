package refactor.common.exception.access;

public class TestAccessException extends AccessException {
    public TestAccessException(long testId, long requesterId) {
        super(testId, requesterId);
    }
}
