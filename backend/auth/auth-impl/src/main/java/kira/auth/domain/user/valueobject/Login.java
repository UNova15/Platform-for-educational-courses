package kira.auth.domain.user.valueobject;

import common.exception.core.DomainValidationException;
import common.exception.named.LengthViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Login {
    public static final int MIN_LOGIN_LENGTH = 8;
    public static final int MAX_LOGIN_LENGTH = 40;

    private final String value;

    public static Login of(String login) {
        if (login == null || login.isBlank()) {
            throw new DomainValidationException("Empty data to create login");
        }

        if (login.length() < MIN_LOGIN_LENGTH || login.length() > MAX_LOGIN_LENGTH) {
            throw new LengthViolationException(
                    "Incorrect login length", "Login", login.length(), MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH);
        }
        return new Login(login);
    }

    public static Login restore(String login) {
        return new Login(login);
    }
}
