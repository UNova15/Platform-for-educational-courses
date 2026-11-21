package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.Valid;
import kira.user.application.ports.in.auth.ResetPasswordUseCase;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class PasswordResetController {
    private final ResetPasswordUseCase resetPasswordService;

    @PostMapping("/password/reset")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@RequestBody @Valid ResetPasswordCommand command) {
        var email = Email.of(command.email());
        var rawPassword = RawPassword.of(command.password());

        resetPasswordService.resetPassword(email, rawPassword);
    }

    @PostMapping("/password/reset/code")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmCode(@RequestBody @Valid CodeConfirmationCommand command) {
        var email = Email.of(command.email());
        var code = Code.of(command.code());

        resetPasswordService.confirmCode(email, code);
    }
}
