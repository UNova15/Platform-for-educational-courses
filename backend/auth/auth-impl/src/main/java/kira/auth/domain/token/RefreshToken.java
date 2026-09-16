package kira.auth.domain.token;

import kira.auth.application.exceptions.AuthExceptionCode;
import kira.auth.domain.token.valueobject.HashedRefreshToken;
import kira.auth.domain.user.User;
import common.domain.Id;
import common.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

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
            throw new DomainValidationException(AuthExceptionCode.INVALID_TOKEN_EXCEPTION, "Token cannot be empty");
        }

        return new RefreshToken(null, userId, token);
    }

    public static RefreshToken restore(Id<RefreshToken> id, Id<User> userId, HashedRefreshToken token) {
        return new RefreshToken(id, userId, token);
    }
}
