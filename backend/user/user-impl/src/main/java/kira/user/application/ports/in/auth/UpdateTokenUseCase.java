package kira.user.application.ports.in.auth;

import jakarta.validation.constraints.NotNull;
import kira.user.domain.token.valueobject.RawRefreshToken;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UpdateTokenUseCase {
    AuthTokens updateTokens(@NotNull RawRefreshToken refreshToken);
}
