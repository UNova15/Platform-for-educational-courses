package kira.auth.application.exceptions;

import common.domain.exception.TypedBaseApplicationException;
import kira.auth.domain.AuthAggregateType;

public class AuthenticationException extends TypedBaseApplicationException {

    public AuthenticationException(String login) {
        super("Cannot authentication user with login: %s".formatted(login), AuthAggregateType.USER);
        withContext("login", login);
    }
}
