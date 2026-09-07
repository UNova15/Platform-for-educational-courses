package refactor.auth.domain.user.valueobject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.common.exception.domain.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RawPassword {
    public static final int MIN_PASSWORD_LENGTH = 8;
    public static final int MAX_PASSWORD_LENGTH = 40;

    private final String value;

    public static RawPassword of(String password) {
        if (password == null || password.isBlank()) {
            throw new DomainValidationException("Empty data to create raw password");
        }

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new DomainValidationException("Incorrect password length: %d . Expected length: min: %d; max: %d"
                    .formatted(password.length(), MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
        }

        return new RawPassword(password);
    }
}
