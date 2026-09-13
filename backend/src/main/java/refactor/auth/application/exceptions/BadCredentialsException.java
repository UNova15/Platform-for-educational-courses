package refactor.auth.application.exceptions;

import refactor.auth.domain.user.valueobject.Login;
import refactor.common.exception.BaseApplicationException;

public class BadCredentialsException extends BaseApplicationException {
    public BadCredentialsException(Login login) {
        super(
                AuthExceptionCode.INVALID_PASSWORD_EXCEPTION,
                "Invalid login or password for user: %s".formatted(login.value()));
        setProperty("login", login.value());
    }
}
