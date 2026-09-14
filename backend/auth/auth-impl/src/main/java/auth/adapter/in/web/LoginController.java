package auth.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import auth.application.ports.in.AuthResult;
import auth.application.ports.in.LoginUseCase;
import refactor.auth.implemetnation.domain.user.valueobject.Login;
import refactor.auth.implemetnation.domain.user.valueobject.RawPassword;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class LoginController {
    private final LoginUseCase loginUseCase;
    private final CookieFactory cookieFactory;

    @PostMapping("/login")
    public ResponseEntity<AuthResult> userLogin(@RequestBody @Valid LoginCommand request) {
        Login login = Login.of(request.login());
        RawPassword password = RawPassword.of(request.password());

        AuthResult authResult = loginUseCase.login(login, password);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(authResult);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(authResult);
    }
}
