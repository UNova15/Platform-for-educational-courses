package kira.auth.application.exceptions;

import common.exception.BaseApplicationException;

public class InvalidTokenException extends BaseApplicationException {

    public InvalidTokenException() {
        super(AuthExceptionCode.INVALID_TOKEN_EXCEPTION, "Invalid or expired token");
    }
}
