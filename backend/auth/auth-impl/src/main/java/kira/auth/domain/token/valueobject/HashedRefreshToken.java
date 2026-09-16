package kira.auth.domain.token.valueobject;

import common.exception.DomainValidationException;
import common.exception.codes.CommonExceptionCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedRefreshToken {
    private final String value;

    public static HashedRefreshToken of(String hashedToken) {
        if (hashedToken == null || hashedToken.isBlank()) {
            throw new DomainValidationException(
                    CommonExceptionCode.DOMAIN_VALIDATION_EXCEPTION, "Hashed token can not be empty");
        }
        return new HashedRefreshToken(hashedToken);
    }

    public static HashedRefreshToken restoreFromHash(String hashedToken) {
        return new HashedRefreshToken(hashedToken);
    }
}
