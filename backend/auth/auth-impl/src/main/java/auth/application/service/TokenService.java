package auth.application.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import auth.application.exceptions.UserNotFoundExceptions;
import refactor.auth.implemetnation.domain.token.valueobject.AccessToken;
import refactor.auth.implemetnation.domain.user.valueobject.Login;
import refactor.common.domain.Id;
import auth.application.exceptions.InvalidTokenException;
import refactor.auth.implemetnation.application.ports.in.AuthResult;
import refactor.auth.implemetnation.application.ports.in.UpdateTokenUseCase;
import refactor.auth.implemetnation.application.ports.out.token.AccessTokenGeneratePort;
import refactor.auth.implemetnation.application.ports.out.token.RefreshTokenGeneratePort;
import refactor.auth.implemetnation.application.ports.out.persistance.RefreshTokenRepositoryPort;
import refactor.auth.implemetnation.application.ports.out.crypto.TokenHashingPort;
import refactor.auth.implemetnation.application.ports.out.persistance.UserLoadPort;
import refactor.auth.implemetnation.domain.token.valueobject.HashedRefreshToken;
import refactor.auth.implemetnation.domain.token.valueobject.RawRefreshToken;
import refactor.auth.implemetnation.domain.token.RefreshToken;
import refactor.auth.implemetnation.domain.user.User;
import refactor.auth.implemetnation.domain.user.valueobject.UserRole;

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

        RefreshToken refreshToken =
                refreshTokenRepositoryPort.load(oldHashedToken).orElseThrow(InvalidTokenException::new);

        refreshTokenRepositoryPort.remove(refreshToken);

        Id<User> userId = refreshToken.userId();

        User user = userLoadPort.loadUserById(userId).orElseThrow(() -> new UserNotFoundExceptions(userId));

        AccessToken newAccessToken = accessTokenGeneratePort.generateAccessToken(userId, user.login(), user.role());
        RawRefreshToken newRefreshToken = refreshTokenGeneratePort.generateRefreshToken();

        HashedRefreshToken hashedToken = hashingPort.hash(newRefreshToken);
        RefreshToken newHashedRefreshToken = RefreshToken.createNew(userId, hashedToken);

        refreshTokenRepositoryPort.save(newHashedRefreshToken);

        return AuthResult.createFrom(userId, user.login(), user.role(), newAccessToken, newRefreshToken);
    }
}
