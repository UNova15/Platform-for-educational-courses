package kira.auth.application.ports.in;

import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LoginUseCase {
    AuthResult login(@NotNull Login login, @NotNull RawPassword password);
}
