package refactor.auth.application.ports.in;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.auth.domain.user.valueobject.Login;
import refactor.auth.domain.user.valueobject.RawPassword;
import refactor.auth.domain.user.valueobject.UserRole;

@Validated
public interface RegistrationUseCase {
    AuthResult registration(@NotNull Login login, @NotNull RawPassword password,@NotNull UserRole role);
}
