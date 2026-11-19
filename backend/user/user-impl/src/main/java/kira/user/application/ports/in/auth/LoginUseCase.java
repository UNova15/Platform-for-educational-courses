package kira.user.application.ports.in.auth;

import jakarta.validation.constraints.NotNull;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.RawPassword;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LoginUseCase {

    AuthTokens login(@NotNull Email email, @NotNull RawPassword password);
}
