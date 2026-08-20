package refactor.auth.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Login {
    public static final int MIN_LOGIN_LENGTH = 4;
    public static final int MAX_LOGIN_LENGTH = 40;

    private final String value;

    public static Login of(String login) {
        if (login == null || login.isBlank()) {
            throw new DomainValidationException("Empty data to create login");
        }
        if (login.length() < MIN_LOGIN_LENGTH || login.length() > MAX_LOGIN_LENGTH) {
            throw new DomainValidationException("Incorrect login length: %d . Expected length: min: %d; max: %d"
                    .formatted(login.length(), MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH));
        }
        return new Login(login);
    }

    public static Login restore(String login) {
        return new Login(login);
    }
}
