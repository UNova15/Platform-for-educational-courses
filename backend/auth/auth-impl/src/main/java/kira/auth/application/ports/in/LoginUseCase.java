package kira.auth.application.ports.in;

import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LoginUseCase {
    enum Error {
        INVALID_CREDENTIALS
    }

    Result<AuthTokens, Error> login(@NotNull Login login, @NotNull RawPassword password);
}
