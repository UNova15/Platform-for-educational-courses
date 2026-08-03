package refactor.user.domain.token;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedToken {
    private final String hashedToken;

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
