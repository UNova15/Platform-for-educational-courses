package kira.auth.application.ports.in;

import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import kira.auth.domain.user.valueobject.UserRole;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RegistrationUseCase {
    AuthResult registration(@NotNull Login login, @NotNull RawPassword password, @NotNull UserRole role);
}
