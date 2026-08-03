package refactor.user.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refactor.common.exception.InvalidTokenException;
import refactor.user.application.ports.in.command.PairOfTokens;
import refactor.user.application.ports.in.usecase.UpdateTokenUseCase;
import refactor.user.application.ports.out.token.AccessTokenGeneratePort;
import refactor.user.application.ports.out.token.RefreshTokenGeneratePort;
import refactor.user.application.ports.out.token.RefreshTokenRepositoryPort;
import refactor.user.application.ports.out.token.TokenHashingPort;
import refactor.user.application.ports.out.user.UserLoadPort;
import refactor.user.domain.token.HashedToken;
import refactor.user.domain.token.RawToken;
import refactor.user.domain.token.RefreshToken;
import refactor.user.domain.user.User;
import refactor.user.domain.user.UserRole;

@Service
@AllArgsConstructor
public class TokenService implements UpdateTokenUseCase {
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

        long userId = refreshToken.getUserId();

        User user = userLoadPort
                .loadUserById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Non-existent user with id: %d and token: %s".formatted(userId, oldHashedToken)));

        String newAccessToken = accessTokenGeneratePort.generateAccessToken(userId, user.getLogin(), user.getRole());
        String newRefreshToken = refreshTokenGeneratePort.generateRefreshToken();

        HashedToken hashedToken = hashingPort.hash(RawToken.of(newRefreshToken));
        RefreshToken newHashedRefreshToken = RefreshToken.createNew(userId, hashedToken);

        refreshTokenRepositoryPort.save(newHashedRefreshToken);

        return new PairOfTokens(userId, user.getLogin(), user.getRole(), newAccessToken, newRefreshToken);
    }
}
