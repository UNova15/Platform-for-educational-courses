package refactor.auth.application.ports.in;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.auth.domain.user.valueobject.Login;
import refactor.auth.domain.user.valueobject.RawPassword;

@Validated
public interface LoginUseCase {
    AuthResult login(@NotNull Login login,@NotNull RawPassword password);
}
