package refactor.auth.domain.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import refactor.common.exception.auth.InvalidTokenException;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class RawToken {
    private final String token;

    public static RawToken of(String token) {
        if (token == null || token.isBlank()) {
            throw new InvalidTokenException("Token can not be empty");
        }
        return new RawToken(token);
    }
}
