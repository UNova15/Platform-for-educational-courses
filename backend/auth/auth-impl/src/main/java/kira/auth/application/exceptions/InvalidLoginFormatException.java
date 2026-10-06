package kira.auth.application.exceptions;

import common.exception.DomainValidationException;

public class InvalidLoginFormatException extends DomainValidationException {

    public InvalidLoginFormatException(int loginLength, int minLoginLength, int maxLoginLength) {
        super("Invalid login length: %d . Expected length: min: %d; max: %d"
                .formatted(loginLength, minLoginLength, maxLoginLength));
        withContext("login", loginLength);
        withContext("maxLength", maxLoginLength);
        withContext("minLength", minLoginLength);
    }
}
