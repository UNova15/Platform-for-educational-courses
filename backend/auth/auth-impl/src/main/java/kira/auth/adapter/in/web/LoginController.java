package kira.auth.adapter.in.web;

import common.domain.Result;
import jakarta.validation.Valid;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.LoginUseCase;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
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
class LoginController {
    private final LoginUseCase loginUseCase;
    private final CookieFactory cookieFactory;

    @PostMapping("/login")
    public ResponseEntity<?> userLogin(@RequestBody @Valid LoginCommand request) {
        Login login = Login.of(request.login());
        RawPassword password = RawPassword.of(request.password());

        Result<AuthTokens, LoginUseCase.Error> authTokens = loginUseCase.login(login, password);

        return authTokens.fold(
                tokens -> ResponseEntity.ok()
                        .header(
                                HttpHeaders.SET_COOKIE,
                                cookieFactory.createDefaultRefreshCookie(tokens).toString())
                        .body(tokens),
                error -> switch (error) {
                    case INVALID_CREDENTIALS ->
                        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
                });
    }
}
