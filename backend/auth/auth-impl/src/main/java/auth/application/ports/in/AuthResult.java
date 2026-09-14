package auth.application.ports.in;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.auth.implemetnation.domain.token.valueobject.AccessToken;
import refactor.auth.implemetnation.domain.token.valueobject.RawRefreshToken;
import refactor.auth.implemetnation.domain.user.User;
import refactor.auth.implemetnation.domain.user.valueobject.Login;
import refactor.auth.implemetnation.domain.user.valueobject.UserRole;
import refactor.common.domain.Id;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthResult {
    private final long id;
    private final String login;
    private final UserRole role;
    private final String accessToken;
    private final String refreshToken;

    public static AuthResult createFrom(
            Id<User> id, Login login, UserRole role, AccessToken accessToken, RawRefreshToken refreshToken) {
        return new AuthResult(id.value(), login.value(), role, accessToken.value(), refreshToken.value());
    }
}
