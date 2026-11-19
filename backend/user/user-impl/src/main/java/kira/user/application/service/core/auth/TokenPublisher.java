package kira.user.application.service.core.auth;

import common.domain.entity.Id;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.out.crypto.TokenHashingPort;
import kira.user.application.ports.out.persistance.token.RefreshTokenSavePort;
import kira.user.application.ports.out.token.AccessTokenGeneratePort;
import kira.user.application.ports.out.token.RefreshTokenGeneratePort;
import kira.user.domain.token.RefreshToken;
import kira.user.domain.token.valueobject.AccessToken;
import kira.user.domain.token.valueobject.HashedRefreshToken;
import kira.user.domain.token.valueobject.RawRefreshToken;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class TokenPublisher {
    private final RefreshTokenSavePort savePort;
    private final TokenHashingPort hashingPort;

    private final AccessTokenGeneratePort accessTokenGeneratePort;
    private final RefreshTokenGeneratePort refreshTokenGeneratePort;

    public AuthTokens issueTokens(Id<User> userId, Login login) {
        AccessToken access = accessTokenGeneratePort.generateAccessToken(userId, login);
        RawRefreshToken refresh = refreshTokenGeneratePort.generateRefreshToken();

        HashedRefreshToken hashedToken = hashingPort.hash(refresh);
        var refreshToken = RefreshToken.create(userId, hashedToken);

        savePort.save(refreshToken);

        return AuthTokens.createFrom(userId, login, access, refresh);
    }
}
