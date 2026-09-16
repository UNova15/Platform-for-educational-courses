package kira.auth.application.exceptions;

import common.exception.codes.ExceptionCode;

public enum AuthExceptionCode implements ExceptionCode {
    INVALID_PASSWORD_OR_LOGIN_EXCEPTION,
    INVALID_TOKEN_EXCEPTION,
    USER_NOT_FOUND_EXCEPTION,
    USER_ALREADY_EXIST_EXCEPTION,

    ERROR_CREATING_USER_DOMAIN_MODEL,
    INVALID_LOGIN_FORMAT,
    INVALID_PASSWORD_FORMAT;

    @Override
    public String getExceptionCode() {
        return this.name();
    }
}
