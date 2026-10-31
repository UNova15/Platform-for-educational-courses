package kira.auth.domain.token.valueobject;

import common.domain.exception.ValueObjectValidationException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RawRefreshToken {
    private final String value;

    public static RawRefreshToken of(String token) {
        if (token == null || token.isBlank()) {
            throw new ValueObjectValidationException("Invalid or expired token");
        }
        return new RawRefreshToken(token);
    }
}
