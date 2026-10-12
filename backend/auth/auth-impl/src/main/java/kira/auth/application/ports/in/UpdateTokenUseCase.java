package kira.auth.application.ports.in;

import jakarta.validation.constraints.NotNull;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UpdateTokenUseCase {
    AuthTokens updateTokens(@NotNull RawRefreshToken refreshToken);
}
