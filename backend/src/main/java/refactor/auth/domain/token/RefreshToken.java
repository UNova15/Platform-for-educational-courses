package refactor.auth.domain.token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
    public static final int MIN_REFRESH_TOKEN_LENGTH = 32;

    private final Long id;
    private final Long userId;
    private final HashedToken token;

    public static RefreshToken createNew(long userId, HashedToken token) {
        if (token == null) {
            throw new IllegalArgumentException("Empty token for user: %d".formatted(userId));
        }

        return new RefreshToken(null, userId, token);
    }

    public static RefreshToken restore(long id, long userId, String token) {
        return new RefreshToken(id, userId, HashedToken.restoreFromHash(token));
    }
}
