package kira.auth.application.service;

import common.domain.Id;
import common.domain.Result;
import java.util.Optional;
import kira.auth.application.exceptions.PersistenceConsistencyException;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.UpdateTokenUseCase;
import kira.auth.application.ports.out.crypto.TokenHashingPort;
import kira.auth.application.ports.out.persistance.RefreshTokenRepositoryPort;
import kira.auth.application.ports.out.persistance.UserLoadPort;
import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import kira.auth.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class TokenUpdateService implements UpdateTokenUseCase {
    private final UserLoadPort userLoadPort;
    private final RefreshTokenRepositoryPort refreshTokenRepositoryPort;

    private final TokenHashingPort hashingPort;

    private final TokenPublisher tokenGenerator;

    @Transactional
    public Result<AuthTokens, Error> updateTokens(RawRefreshToken oldRefresh) {
        HashedRefreshToken oldHashedToken = hashingPort.hash(oldRefresh);

        Optional<RefreshToken> loadedToken = refreshTokenRepositoryPort.load(oldHashedToken);

        if (loadedToken.isEmpty()) {
            return Result.failure(Error.INVALID_REFRESH_TOKEN);
        }
        RefreshToken refreshToken = loadedToken.get();

        Id<User> userId = refreshToken.userId();

        // невалидное состояние бд - токен в бд есть, но пользователя нет
        User user = userLoadPort.loadUserById(userId).orElseThrow(() -> new PersistenceConsistencyException(
                        "The user does not exist, his token exists", userId)
                .withContext("tokenId", refreshToken.id())
                .withContext("token", refreshToken.token()));

        refreshTokenRepositoryPort.remove(refreshToken);
        AuthTokens tokens = tokenGenerator.issueTokens(userId, user.login());
        return Result.success(tokens);
    }
}
