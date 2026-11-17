package kira.user.application.exceptions;

import common.domain.exception.TypedBaseApplicationException;
import kira.user.domain.AuthAggregateType;

public class AuthenticationException extends TypedBaseApplicationException {

    public AuthenticationException(String login) {
        super("Cannot authentication user with login: %s".formatted(login), AuthAggregateType.USER);
        withContext("login", login);
    }
}
