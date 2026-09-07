package refactor.auth.domain.token.valueobject;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import refactor.common.exception.auth.InvalidTokenException;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RawRefreshToken {
    private final String value;

    public static RawRefreshToken of(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Token can not be empty");
        }
        return new RawRefreshToken(token);
    }
}
