package kira.auth.application.ports.in;

import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RegistrationUseCase {
    enum Error {
        USER_ALREADY_EXISTS,
    }

    Result<AuthTokens, Error> registration(@NotNull Login login, @NotNull RawPassword password);
}
