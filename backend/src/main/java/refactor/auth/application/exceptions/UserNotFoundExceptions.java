package refactor.auth.application.exceptions;

import refactor.auth.domain.user.User;
import refactor.common.domain.Id;
import refactor.common.exception.NotFoundException;

public class UserNotFoundExceptions extends NotFoundException {

    public UserNotFoundExceptions(Id<User> userId) {
        super(
                AuthExceptionCode.USER_NOT_FOUND_EXCEPTION,
                "User with ID: %s not found".formatted(userId.value()),
                userId);
    }
}
