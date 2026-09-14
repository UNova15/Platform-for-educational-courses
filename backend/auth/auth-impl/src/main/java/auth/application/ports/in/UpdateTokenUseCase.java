package auth.application.ports.in;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.auth.implemetnation.domain.token.valueobject.RawRefreshToken;

@Validated
public interface UpdateTokenUseCase {
    AuthResult updateTokens(@NotNull RawRefreshToken refreshToken);
}
