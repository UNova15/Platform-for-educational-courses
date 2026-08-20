package refactor.common.exception.domain;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(String login) {
        super("User with login: %s not found".formatted(login));
    }
}
