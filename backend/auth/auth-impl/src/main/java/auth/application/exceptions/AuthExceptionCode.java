package auth.application.exceptions;

import refactor.common.exception.codes.ExceptionCode;

public enum AuthExceptionCode implements ExceptionCode {
    INVALID_PASSWORD_EXCEPTION,
    USER_NOT_FOUND_EXCEPTION,
    INVALID_TOKEN_EXCEPTION,
    USER_ALREADY_EXIST;

    @Override
    public String getExceptionCode() {
        return this.name();
    }
}
