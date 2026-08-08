package refactor.auth.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refactor.auth.application.ports.in.usecase.LoginCommand;
import refactor.auth.application.ports.in.usecase.PairOfTokens;
import refactor.auth.application.ports.in.usecase.LoginUseCase;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
class LoginController {
    private final LoginUseCase loginUseCase;
    private final CookieFactory cookieFactory;

    @PostMapping("/login")
    public ResponseEntity<PairOfTokens> userLogin(@RequestBody @Valid LoginCommand request) {
        PairOfTokens pairOfTokens = loginUseCase.login(request);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(pairOfTokens);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(pairOfTokens);
    }
}
