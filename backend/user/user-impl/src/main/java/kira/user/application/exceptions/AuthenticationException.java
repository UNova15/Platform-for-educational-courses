package kira.user.application.exceptions;

import common.domain.exception.TypedBaseApplicationException;
import kira.user.domain.AuthAggregateType;

public class AuthenticationException extends TypedBaseApplicationException {

    public AuthenticationException(String email) {
        super("Cannot authentication user with email: %s".formatted(email), AuthAggregateType.USER);
        withContext("email", email);
    }
}
