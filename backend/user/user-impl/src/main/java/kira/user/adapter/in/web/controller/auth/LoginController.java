package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.Valid;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.in.auth.LoginUseCase;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class LoginController {
    private final LoginUseCase loginUseCase;
    private final CookieFactory cookieFactory;

    @PostMapping("/login")
    public ResponseEntity<AuthTokens> login(@RequestBody @Valid LoginCommand request) {
        var email = Email.of(request.email());
        var password = RawPassword.of(request.password());

        AuthTokens authTokens = loginUseCase.login(email, password);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        cookieFactory.createDefaultRefreshCookie(authTokens).toString())
                .body(authTokens);
    }
}
