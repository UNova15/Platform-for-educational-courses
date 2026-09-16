package kira.auth.application.service;

import kira.auth.application.exceptions.AuthExceptionCode;
import kira.auth.application.ports.in.AuthResult;
import kira.auth.application.ports.in.UpdateTokenUseCase;
import kira.auth.application.ports.out.crypto.TokenHashingPort;
import kira.auth.application.ports.out.persistance.RefreshTokenRepositoryPort;
import kira.auth.application.ports.out.persistance.UserLoadPort;
import kira.auth.application.ports.out.token.AccessTokenGeneratePort;
import kira.auth.application.ports.out.token.RefreshTokenGeneratePort;
import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.AccessToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.UserRole;
import common.domain.Id;
import common.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kira.auth.application.exceptions.InvalidTokenException;

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

        User user = userLoadPort
                .loadUserById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AuthExceptionCode.USER_NOT_FOUND_EXCEPTION, User.class, userId));

        AccessToken newAccessToken = accessTokenGeneratePort.generateAccessToken(userId, user.login(), user.role());
        RawRefreshToken newRefreshToken = refreshTokenGeneratePort.generateRefreshToken();

        HashedRefreshToken hashedToken = hashingPort.hash(newRefreshToken);
        RefreshToken newHashedRefreshToken = RefreshToken.createNew(userId, hashedToken);

        refreshTokenRepositoryPort.save(newHashedRefreshToken);

        return AuthResult.createFrom(userId, user.login(), user.role(), newAccessToken, newRefreshToken);
    }
}
