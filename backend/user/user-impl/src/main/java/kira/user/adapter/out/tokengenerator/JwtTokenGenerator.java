package kira.user.adapter.out.tokengenerator;

import common.domain.entity.Id;
import kira.infrastructure.api.AccessTokenGenerator;
import kira.user.application.ports.out.token.AccessTokenGeneratePort;
import kira.user.domain.token.valueobject.AccessToken;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Login;
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
