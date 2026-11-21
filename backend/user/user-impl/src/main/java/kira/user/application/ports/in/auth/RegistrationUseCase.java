package kira.user.application.ports.in.auth;

import jakarta.validation.constraints.NotNull;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.RawPassword;
import kira.user.domain.user.valueobject.Username;
import org.springframework.validation.annotation.Validated;

@Validated
public interface RegistrationUseCase {

    void registration(@NotNull Username username, @NotNull Email email, @NotNull RawPassword password);

    AuthTokens confirmCode(@NotNull Email email, @NotNull Code code);
}
