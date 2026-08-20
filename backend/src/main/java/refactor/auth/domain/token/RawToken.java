package refactor.auth.domain.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import refactor.common.exception.auth.InvalidTokenException;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RawToken {
    private final String value;

    public static RawToken of(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Token can not be empty");
        }
        return new RawToken(token);
    }
}
