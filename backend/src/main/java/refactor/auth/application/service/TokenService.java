package refactor.auth.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refactor.auth.domain.token.valueobject.AccessToken;
import refactor.auth.domain.user.valueobject.Login;
import refactor.common.domain.Id;
import refactor.common.exception.auth.InvalidTokenException;
import refactor.auth.application.ports.in.AuthResult;
import refactor.auth.application.ports.in.UpdateTokenUseCase;
import refactor.auth.application.ports.out.token.AccessTokenGeneratePort;
import refactor.auth.application.ports.out.token.RefreshTokenGeneratePort;
import refactor.auth.application.ports.out.persistance.RefreshTokenRepositoryPort;
import refactor.auth.application.ports.out.crypto.TokenHashingPort;
import refactor.auth.application.ports.out.persistance.UserLoadPort;
import refactor.auth.domain.token.valueobject.HashedRefreshToken;
import refactor.auth.domain.token.valueobject.RawRefreshToken;
import refactor.auth.domain.token.RefreshToken;
import refactor.auth.domain.user.User;
import refactor.auth.domain.user.valueobject.UserRole;

@Service
@AllArgsConstructor
class TokenService implements UpdateTokenUseCase {
    private final UserLoadPort userLoadPort;
    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;

    private final TokenHashingPort hashingPort;

    private final AccessTokenGeneratePort accessTokenGeneratePort;
    private final RefreshTokenGeneratePort refreshTokenGeneratePort;

    public AuthResult createTokens(Id<User> userId, Login login, UserRole role) {

        AccessToken access = accessTokenGeneratePort.generateAccessToken(userId, login, role);
        RawRefreshToken refresh = refreshTokenGeneratePort.generateRefreshToken();

        HashedRefreshToken hashedToken = hashingPort.hash(refresh);

        RefreshToken refreshToken = RefreshToken.createNew(userId, hashedToken);
        refreshTokenRepositoryPort.save(refreshToken);

        return AuthResult.createFrom(userId, login, role, access, refresh);
    }

    @Transactional
    public AuthResult updateTokens(RawRefreshToken oldRefresh) {
        HashedRefreshToken oldHashedToken = hashingPort.hash(oldRefresh);

        RefreshToken refreshToken = refreshTokenRepositoryPort
                .load(oldHashedToken)
                .orElseThrow(() -> new InvalidTokenException("Invalid refresh token: %s".formatted(oldHashedToken)));

        refreshTokenRepositoryPort.remove(refreshToken);

        Id<User> userId = refreshToken.userId();

        User user = userLoadPort
                .loadUserById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "Non-existent user with id: %d and token: %s".formatted(userId.value(), oldHashedToken)));

        AccessToken newAccessToken = accessTokenGeneratePort.generateAccessToken(userId, user.login(), user.role());
        RawRefreshToken newRefreshToken = refreshTokenGeneratePort.generateRefreshToken();

        HashedRefreshToken hashedToken = hashingPort.hash(newRefreshToken);
        RefreshToken newHashedRefreshToken = RefreshToken.createNew(userId, hashedToken);

        refreshTokenRepositoryPort.save(newHashedRefreshToken);

        return AuthResult.createFrom(userId, user.login(), user.role(), newAccessToken, newRefreshToken);
    }
}
