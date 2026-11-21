package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.Valid;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.in.auth.RegistrationUseCase;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.RawPassword;
import kira.user.domain.user.valueobject.Username;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class RegistrationController {
    private final RegistrationUseCase registrationService;
    private final CookieFactory cookieFactory;

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void registration(@RequestBody @Valid RegistrationCommand request) {
        var username = Username.of(request.username());
        var password = RawPassword.of(request.password());
        var email = Email.of(request.email());

        registrationService.registration(username, email, password);
    }

    @PostMapping("/registration/code")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AuthResponse> codeConfirmation(@RequestBody @Valid CodeConfirmationCommand command) {
        var code = Code.of(command.code());
        var email = Email.of(command.email());

        AuthTokens tokens = registrationService.confirmCode(email, code);

        var response = new AuthResponse(tokens.id(), tokens.username(), tokens.accessToken());
        return ResponseEntity.status(HttpStatus.CREATED)
                .header(
                        HttpHeaders.SET_COOKIE,
                        cookieFactory.createDefaultRefreshCookie(tokens).toString())
                .body(response);
    }
}
