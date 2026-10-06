package kira.auth.application.exceptions;

import common.exception.DomainValidationException;

public class InvalidPasswordFormatException extends DomainValidationException {
    public InvalidPasswordFormatException(int passwordLength, int minPasswordLength, int maxPasswordLength) {
        super("Incorrect password length: %d . Expected length: min: %d; max: %d"
                .formatted(passwordLength, minPasswordLength, maxPasswordLength));
        withContext("login", passwordLength);
        withContext("maxLength", maxPasswordLength);
        withContext("minLength", minPasswordLength);
    }
}
