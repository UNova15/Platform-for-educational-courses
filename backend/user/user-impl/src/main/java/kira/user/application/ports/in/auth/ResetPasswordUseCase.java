package kira.user.application.ports.in.auth;

import jakarta.validation.constraints.NotNull;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.RawPassword;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ResetPasswordUseCase {
    void resetPassword(@NotNull Email email, @NotNull RawPassword rawPassword);

    void confirmCode(@NotNull Email email, @NotNull Code inputCode);
}
