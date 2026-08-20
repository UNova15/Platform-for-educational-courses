package refactor.auth.domain.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedToken {
    private final String value;

    public static HashedToken of(String hashedToken) {
        if (hashedToken == null || hashedToken.isBlank()) {
            throw new IllegalArgumentException("Hashed token can not be empty");
        }
        return new HashedToken(hashedToken);
    }

    public static HashedToken restoreFromHash(String hashedToken) {
        return new HashedToken(hashedToken);
    }
}
