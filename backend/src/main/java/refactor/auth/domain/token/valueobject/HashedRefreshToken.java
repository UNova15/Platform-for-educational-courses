package refactor.auth.domain.token.valueobject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import refactor.common.exception.DomainValidationException;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedRefreshToken {
    private final String value;

    public static HashedRefreshToken of(String hashedToken) {
        if (hashedToken == null || hashedToken.isBlank()) {
            throw new DomainValidationException("Hashed token can not be empty");
        }
        return new HashedRefreshToken(hashedToken);
    }

    public static HashedRefreshToken restoreFromHash(String hashedToken) {
        return new HashedRefreshToken(hashedToken);
    }
}
