package refactor.auth.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refactor.common.exception.auth.InvalidTokenException;
import refactor.auth.application.ports.in.PairOfTokens;
import refactor.auth.application.ports.in.UpdateTokenUseCase;
import refactor.auth.application.ports.out.token.AccessTokenGeneratePort;
import refactor.auth.application.ports.out.token.RefreshTokenGeneratePort;
import refactor.auth.application.ports.out.persistance.RefreshTokenRepositoryPort;
import refactor.auth.application.ports.out.crypto.TokenHashingPort;
import refactor.auth.application.ports.out.persistance.UserLoadPort;
import refactor.auth.domain.token.HashedToken;
import refactor.auth.domain.token.RawToken;
import refactor.auth.domain.token.RefreshToken;
import refactor.auth.domain.user.User;
import refactor.auth.domain.user.UserRole;

@Service
@AllArgsConstructor
class TokenService implements UpdateTokenUseCase {
    private final UserLoadPort userLoadPort;
    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;

    private final TokenHashingPort hashingPort;

    private final AccessTokenGeneratePort accessTokenGeneratePort;
    private final RefreshTokenGeneratePort refreshTokenGeneratePort;

    public PairOfTokens createTokens(long userId, String login, UserRole role) {
        String jwt = accessTokenGeneratePort.generateAccessToken(userId, login, role);
        String refresh = refreshTokenGeneratePort.generateRefreshToken();

        HashedToken hashedToken = hashingPort.hash(RawToken.of(refresh));

        RefreshToken refreshToken = RefreshToken.createNew(userId, hashedToken);
        refreshTokenRepositoryPort.save(refreshToken);

        return new PairOfTokens(userId, login, role, jwt, refresh);
    }

    @Transactional
    public PairOfTokens updateTokens(String oldRawRefreshToken) {
        RawToken oldRefresh = RawToken.of(oldRawRefreshToken);
        HashedToken oldHashedToken = hashingPort.hash(oldRefresh);

        RefreshToken refreshToken = refreshTokenRepositoryPort
                .load(oldHashedToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token: %s".formatted(oldHashedToken)));

        refreshTokenRepositoryPort.remove(refreshToken);

        long userId = refreshToken.userId();

        User user = userLoadPort
                .loadUserById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Non-existent user with id: %d and token: %s".formatted(userId, oldHashedToken)));

        String newAccessToken =
                accessTokenGeneratePort.generateAccessToken(userId, user.login().value(), user.role());
        String newRefreshToken = refreshTokenGeneratePort.generateRefreshToken();

        HashedToken hashedToken = hashingPort.hash(RawToken.of(newRefreshToken));
        RefreshToken newHashedRefreshToken = RefreshToken.createNew(userId, hashedToken);

        refreshTokenRepositoryPort.save(newHashedRefreshToken);

        return new PairOfTokens(userId, user.login().value(), user.role(), newAccessToken, newRefreshToken);
    }
}
