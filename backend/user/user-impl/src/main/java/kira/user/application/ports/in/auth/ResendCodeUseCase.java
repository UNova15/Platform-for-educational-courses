package kira.user.application.ports.in.auth;

import jakarta.validation.constraints.NotNull;
import kira.user.domain.user.valueobject.Email;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ResendCodeUseCase {
    void resend(@NotNull Email email);
}
