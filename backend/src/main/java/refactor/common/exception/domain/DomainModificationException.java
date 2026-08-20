package refactor.common.exception.domain;

public class DomainModificationException extends RuntimeException {
    public DomainModificationException(String message) {
        super("Error modifying the model due to: %s .".formatted(message));
    }
}
