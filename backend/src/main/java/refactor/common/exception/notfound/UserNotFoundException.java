package refactor.common.exception.notfound;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(String login) {
        super("User with login: %s not found".formatted(login));
    }
}
