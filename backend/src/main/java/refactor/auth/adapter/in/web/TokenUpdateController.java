package refactor.auth.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import refactor.auth.application.ports.in.PairOfTokens;
import refactor.auth.application.ports.in.UpdateTokenUseCase;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
class TokenUpdateController {
    private final UpdateTokenUseCase updateTokenUseCase;
    private final CookieFactory cookieFactory;

    @PostMapping("/refresh")
    public ResponseEntity<PairOfTokens> refresh(@CookieValue("refreshToken") String refreshToken) {
        PairOfTokens pairOfTokens = updateTokenUseCase.updateTokens(refreshToken);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(pairOfTokens);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(pairOfTokens);
    }
}
