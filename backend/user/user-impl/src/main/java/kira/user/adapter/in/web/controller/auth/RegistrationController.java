package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.Valid;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.in.auth.RegistrationUseCase;
import kira.user.domain.user.valueobject.Username;
import kira.user.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class RegistrationController {
    private final RegistrationUseCase registrationService;
    private final CookieFactory cookieFactory;

    @PostMapping("/registration")
    public ResponseEntity<AuthTokens> registration(@RequestBody @Valid RegistrationCommand request) {
        var login = Username.of(request.login());
        var password = RawPassword.of(request.password());

        AuthTokens authTokens = registrationService.registration(login, password);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(
                        HttpHeaders.SET_COOKIE,
                        cookieFactory.createDefaultRefreshCookie(authTokens).toString())
                .body(authTokens);
    }
}
