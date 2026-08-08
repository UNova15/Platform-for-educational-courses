package refactor.common.exception.auth;

public class InvalidPasswordException extends AuthenticationException {
    public InvalidPasswordException(String login) {
        super("Invalid password for user: %s".formatted(login));
    }
}
