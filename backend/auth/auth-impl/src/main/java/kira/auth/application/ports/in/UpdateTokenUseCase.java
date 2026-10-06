package kira.auth.application.ports.in;

import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UpdateTokenUseCase {
    enum Error {
        INVALID_REFRESH_TOKEN
    }

    Result<AuthTokens, Error> updateTokens(@NotNull RawRefreshToken refreshToken);
}
