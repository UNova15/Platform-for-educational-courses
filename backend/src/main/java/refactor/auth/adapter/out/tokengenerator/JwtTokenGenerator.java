package refactor.auth.adapter.out.tokengenerator;

import lombok.RequiredArgsConstructor;
import refactor.auth.application.ports.out.token.AccessTokenGeneratePort;
import refactor.auth.domain.token.valueobject.AccessToken;
import refactor.auth.domain.user.User;
import refactor.auth.domain.user.valueobject.Login;
import refactor.auth.domain.user.valueobject.UserRole;
import refactor.common.domain.Id;
import refactor.infrastructure.accesstoken.AccessTokenGenerator;

@RequiredArgsConstructor
class JwtTokenGenerator implements AccessTokenGeneratePort {
    private final AccessTokenGenerator generator;

    @Override
    public AccessToken generateAccessToken(Id<User> id, Login login, UserRole role) {
        String token = generator.generateAccessToken(id.value(), login.value(), role.toString());
        return AccessToken.of(token);
    }
}
