package kira.auth.domain.user.valueobject;

import common.exception.DomainValidationException;
import common.exception.codes.CommonExceptionCode;
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
            throw new DomainValidationException(
                    CommonExceptionCode.DOMAIN_VALIDATION_EXCEPTION, "Hashed password cannot be empty");
        }

        return new HashedPassword(hashedPassword);
    }

    public static HashedPassword restoreFromHash(String hashedPassword) {
        return new HashedPassword(hashedPassword);
    }
}
