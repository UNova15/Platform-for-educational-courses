package kira.infrastructure.exceptions;

import common.exception.core.BaseApplicationException;

public class TokenDecodeException extends BaseApplicationException {

    public TokenDecodeException(String token) {
        super("Error decoding token");
        withContext("token", token);
    }
}
