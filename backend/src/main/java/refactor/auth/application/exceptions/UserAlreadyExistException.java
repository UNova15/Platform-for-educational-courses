package refactor.auth.application.exceptions;

import refactor.auth.domain.user.valueobject.Login;
import refactor.common.exception.AlreadyExistException;

public class UserAlreadyExistException extends AlreadyExistException {

    public UserAlreadyExistException(Login login) {
        super(AuthExceptionCode.USER_ALREADY_EXIST, "User with Login: %s already exist".formatted(login));
        setProperty("login", login);
    }
}
