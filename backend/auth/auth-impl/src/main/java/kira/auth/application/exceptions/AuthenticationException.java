package kira.auth.application.exceptions;

import common.exception.core.BaseApplicationException;

public class AuthenticationException extends BaseApplicationException {

    public AuthenticationException(String login) {
        super("Cannot authentication user with login: %s".formatted(login));
        withContext("login", login);
    }
}
