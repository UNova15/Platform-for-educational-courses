package auth.adapter.out.tokengenerator;

import lombok.RequiredArgsConstructor;
import auth.application.ports.out.token.AccessTokenGeneratePort;
import refactor.auth.implemetnation.domain.token.valueobject.AccessToken;
import refactor.auth.implemetnation.domain.user.User;
import refactor.auth.implemetnation.domain.user.valueobject.Login;
import refactor.auth.implemetnation.domain.user.valueobject.UserRole;
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
