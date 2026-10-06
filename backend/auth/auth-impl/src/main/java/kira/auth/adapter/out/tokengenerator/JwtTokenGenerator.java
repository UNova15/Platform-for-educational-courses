package kira.auth.adapter.out.tokengenerator;

import common.domain.Id;
import kira.auth.application.ports.out.token.AccessTokenGeneratePort;
import kira.auth.domain.token.valueobject.AccessToken;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;
import kira.infrastructure.api.AccessTokenGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class JwtTokenGenerator implements AccessTokenGeneratePort {
    private final AccessTokenGenerator generator;

    @Override
    public AccessToken generateAccessToken(Id<User> id, Login login) {
        String token = generator.generateAccessToken(id.value(), login.value());
        return AccessToken.of(token);
    }
}
