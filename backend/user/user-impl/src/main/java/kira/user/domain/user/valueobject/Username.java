package kira.user.domain.user.valueobject;

import common.domain.exception.LengthViolationException;
import common.domain.exception.ValueObjectValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Username {
    public static final int MIN_LOGIN_LENGTH = 8;
    public static final int MAX_LOGIN_LENGTH = 40;

    private final String value;

    public static Username of(String login) {
        if (login == null || login.isBlank()) {
            throw new ValueObjectValidationException("Empty data to create username");
        }

        if (login.length() < MIN_LOGIN_LENGTH || login.length() > MAX_LOGIN_LENGTH) {
            throw new LengthViolationException(
                    "Incorrect username length", login.length(), MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH);
        }
        return new Username(login);
    }
}
