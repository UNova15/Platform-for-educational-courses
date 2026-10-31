package kira.auth.application.ports.in;

import jakarta.validation.constraints.NotNull;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RegistrationUseCase {

    AuthTokens registration(@NotNull Login login, @NotNull RawPassword password);
}
