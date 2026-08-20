package refactor.common.exception.domain;

public class DomainValidationException extends RuntimeException {
    public DomainValidationException(String message) {
        super("Error creating the model due to: %s".formatted(message));
    }
}
