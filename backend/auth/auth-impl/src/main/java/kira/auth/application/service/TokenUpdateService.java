package kira.auth.application.service;

import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.auth.application.exceptions.PersistenceConsistencyException;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.UpdateTokenUseCase;
import kira.auth.application.ports.out.crypto.TokenHashingPort;
import kira.auth.application.ports.out.persistance.token.RefreshTokenLoadPort;
import kira.auth.application.ports.out.persistance.token.RefreshTokenRemovePort;
import kira.auth.application.ports.out.persistance.user.UserLoadPort;
import kira.auth.domain.AuthAggregateType;
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
    private final RefreshTokenRemovePort removePort;
    private final RefreshTokenLoadPort loadPort;

    private final TokenHashingPort hashingPort;
    private final TokenPublisher tokenGenerator;

    @Transactional
    public AuthTokens updateTokens(RawRefreshToken oldRefresh) {
        HashedRefreshToken oldHashedToken = hashingPort.hash(oldRefresh);

        RefreshToken refreshToken = loadPort.load(oldHashedToken)
                .orElseThrow(() -> new ResourceNotFoundException(AuthAggregateType.ACCESS_TOKEN)
                        .withContext("oldToken", oldRefresh.value()));

        Id<User> userId = refreshToken.userId();

        // невалидное состояние бд - токен в бд есть, но пользователя нет
        User user = userLoadPort.loadUserById(userId).orElseThrow(() -> new PersistenceConsistencyException(
                        "The user does not exist, his token exists", AuthAggregateType.USER, userId)
                .withContext("tokenId", refreshToken.id())
                .withContext("token", refreshToken.token()));

        removePort.remove(refreshToken);
        return tokenGenerator.issueTokens(userId, user.login());
    }
}
