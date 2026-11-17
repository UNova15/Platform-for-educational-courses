package kira.user.application.ports.in.auth;

import jakarta.validation.constraints.NotNull;
import kira.user.domain.user.valueobject.Username;
import kira.user.domain.user.valueobject.RawPassword;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RegistrationUseCase {

    AuthTokens registration(@NotNull Username username, @NotNull RawPassword password);
}
