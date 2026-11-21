package kira.user.application.ports.in.auth;

import common.domain.entity.Id;
import kira.user.domain.token.valueobject.AccessToken;
import kira.user.domain.token.valueobject.RawRefreshToken;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Username;

public record AuthTokens(long id, String username, String accessToken, String refreshToken) {
    public static AuthTokens createFrom(
            Id<User> id, Username username, AccessToken accessToken, RawRefreshToken refreshToken) {
        return new AuthTokens(id.value(), username.value(), accessToken.value(), refreshToken.value());
    }
}
