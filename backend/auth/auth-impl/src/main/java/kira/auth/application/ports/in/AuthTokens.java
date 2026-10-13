package kira.auth.application.ports.in;

import common.domain.entity.Id;
import kira.auth.domain.token.valueobject.AccessToken;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;

public record AuthTokens(long id, String login, String accessToken, String refreshToken) {
    public static AuthTokens createFrom(
            Id<User> id, Login login, AccessToken accessToken, RawRefreshToken refreshToken) {
        return new AuthTokens(id.value(), login.value(), accessToken.value(), refreshToken.value());
    }
}
