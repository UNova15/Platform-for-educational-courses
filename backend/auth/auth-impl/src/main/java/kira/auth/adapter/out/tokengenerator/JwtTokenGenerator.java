package kira.auth.adapter.out.tokengenerator;

import kira.auth.domain.token.valueobject.AccessToken;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.UserRole;
import common.domain.Id;
import kira.infrastructure.api.AccessTokenGenerator;
import lombok.RequiredArgsConstructor;
import kira.auth.application.ports.out.token.AccessTokenGeneratePort;

@RequiredArgsConstructor
class JwtTokenGenerator implements AccessTokenGeneratePort {
    private final AccessTokenGenerator generator;

    @Override
    public AccessToken generateAccessToken(Id<User> id, Login login, UserRole role) {
        String token = generator.generateAccessToken(id.value(), login.value(), role.toString());
        return AccessToken.of(token);
    }
}
