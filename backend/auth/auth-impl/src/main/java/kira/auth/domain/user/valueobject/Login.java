package kira.auth.domain.user.valueobject;

import kira.auth.application.exceptions.AuthExceptionCode;
import common.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Login {
    public static final int MIN_LOGIN_LENGTH = 4;
    public static final int MAX_LOGIN_LENGTH = 40;

    private final String value;

    public static Login of(String login) {
        if (login == null || login.isBlank()) {
            throw new DomainValidationException(AuthExceptionCode.INVALID_LOGIN_FORMAT, "Empty data to create login");
        }
        if (login.length() < MIN_LOGIN_LENGTH || login.length() > MAX_LOGIN_LENGTH) {
            throw new DomainValidationException(
                    AuthExceptionCode.INVALID_LOGIN_FORMAT,
                    "Incorrect login length: %d . Expected length: min: %d; max: %d"
                            .formatted(login.length(), MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH),
                    Map.of("length", login.length()));
        }
        return new Login(login);
    }

    public static Login restore(String login) {
        return new Login(login);
    }
}
