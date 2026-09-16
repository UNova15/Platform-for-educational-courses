package kira.auth.application.ports.in;

import kira.auth.domain.token.valueobject.RawRefreshToken;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UpdateTokenUseCase {
    AuthResult updateTokens(@NotNull RawRefreshToken refreshToken);
}
