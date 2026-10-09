package kira.infrastructure.exceptions;

import common.exception.core.BaseApplicationException;

public class TokenDecodeException extends BaseApplicationException {

    public TokenDecodeException() {
        super("Error decoding token");
    }
}
