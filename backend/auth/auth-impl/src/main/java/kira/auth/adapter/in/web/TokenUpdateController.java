package kira.auth.adapter.in.web;

import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.UpdateTokenUseCase;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
class TokenUpdateController {
    private final UpdateTokenUseCase updateTokenUseCase;
    private final CookieFactory cookieFactory;

    @PostMapping("/refresh")
    public ResponseEntity<AuthTokens> refresh(@CookieValue("refreshToken") String refreshToken) {
        RawRefreshToken token = RawRefreshToken.of(refreshToken);

        AuthTokens authTokens = updateTokenUseCase.updateTokens(token);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.SET_COOKIE,
                        cookieFactory.createDefaultRefreshCookie(authTokens).toString())
                .body(authTokens);
    }
}
