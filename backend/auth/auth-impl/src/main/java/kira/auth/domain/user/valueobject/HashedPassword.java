package kira.auth.domain.user.valueobject;

import common.exception.core.DomainValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedPassword {
    private final String value;

    public static HashedPassword of(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new DomainValidationException("Hashed password cannot be empty");
        }

        return new HashedPassword(hashedPassword);
    }

    public static HashedPassword restoreFromHash(String hashedPassword) {
        return new HashedPassword(hashedPassword);
    }
}
