package kira.user.application.ports.in.auth;

import jakarta.validation.constraints.NotNull;
import kira.user.domain.user.valueobject.Login;
import kira.user.domain.user.valueobject.RawPassword;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LoginUseCase {

    AuthTokens login(@NotNull Login login, @NotNull RawPassword password);
}
