package kira.auth.application.service;

import common.domain.entity.Id;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.out.crypto.TokenHashingPort;
import kira.auth.application.ports.out.persistance.token.RefreshTokenSavePort;
import kira.auth.application.ports.out.token.AccessTokenGeneratePort;
import kira.auth.application.ports.out.token.RefreshTokenGeneratePort;
import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.AccessToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;
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
        RefreshToken refreshToken = RefreshToken.create(userId, hashedToken);

        savePort.save(refreshToken);

        return AuthTokens.createFrom(userId, login, access, refresh);
    }
}
