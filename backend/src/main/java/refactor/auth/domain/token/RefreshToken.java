package refactor.auth.domain.token;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.auth.domain.token.valueobject.HashedRefreshToken;
import refactor.auth.domain.user.User;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
    public static final int MIN_REFRESH_TOKEN_LENGTH = 32;

    private final Id<RefreshToken> id;
    private final Id<User> userId;
    private final HashedRefreshToken token;

    public static RefreshToken createNew(Id<User> userId, HashedRefreshToken token) {
        if (token == null) {
            throw new DomainValidationException("Token cannot be empty");
        }

        return new RefreshToken(null, userId, token);
    }

    public static RefreshToken restore(Id<RefreshToken> id, Id<User> userId, HashedRefreshToken token) {
        return new RefreshToken(id, userId, token);
    }
}
