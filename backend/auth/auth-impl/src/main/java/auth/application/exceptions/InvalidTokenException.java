package auth.application.exceptions;

import refactor.common.exception.BaseApplicationException;

public class InvalidTokenException extends BaseApplicationException {

    public InvalidTokenException() {
        super(AuthExceptionCode.INVALID_TOKEN_EXCEPTION, "Provided token is invalid or expired");
    }
}
