package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.Valid;
import kira.user.application.ports.in.auth.ResendCodeUseCase;
import kira.user.domain.user.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/code/repetition")
@RequiredArgsConstructor
public class CodeRepeatController {
    private final ResendCodeUseCase resendService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void repeatSending(@RequestBody @Valid RepeatCodeCommand command) {
        var email = Email.of(command.email());

        resendService.resend(email);
    }
}
