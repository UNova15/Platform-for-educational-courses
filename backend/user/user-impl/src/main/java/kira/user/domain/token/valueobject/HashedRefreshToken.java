package kira.user.domain.token.valueobject;

import common.domain.exception.ValueObjectValidationException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedRefreshToken {
    private final String value;

    public static HashedRefreshToken of(String hashedToken) {
        if (hashedToken == null || hashedToken.isBlank()) {
            throw new ValueObjectValidationException("Hashed token can not be empty");
        }
        return new HashedRefreshToken(hashedToken);
    }
}
