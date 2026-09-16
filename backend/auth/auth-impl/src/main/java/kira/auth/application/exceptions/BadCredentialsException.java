package kira.auth.application.exceptions;

import kira.auth.domain.user.valueobject.Login;
import common.exception.BaseApplicationException;

public class BadCredentialsException extends BaseApplicationException {
    private static final String TEMPLATE = "Incorrect password or login with user: %s";

    public BadCredentialsException(Login login) {
        super(AuthExceptionCode.INVALID_PASSWORD_OR_LOGIN_EXCEPTION, TEMPLATE.formatted(login.value()));
        setProperty("login", login.value());
    }
}
