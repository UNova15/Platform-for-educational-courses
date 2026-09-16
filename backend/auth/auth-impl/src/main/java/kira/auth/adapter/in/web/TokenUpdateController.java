package kira.auth.adapter.in.web;

import kira.auth.domain.token.valueobject.RawRefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kira.auth.application.ports.in.AuthResult;
import kira.auth.application.ports.in.UpdateTokenUseCase;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
class TokenUpdateController {
    private final UpdateTokenUseCase updateTokenUseCase;
    private final CookieFactory cookieFactory;

    @PostMapping("/refresh")
    public ResponseEntity<AuthResult> refresh(@CookieValue("refreshToken") String refreshToken) {
        RawRefreshToken token = RawRefreshToken.of(refreshToken);

        AuthResult authResult = updateTokenUseCase.updateTokens(token);

        ResponseCookie responseCookie = cookieFactory.createDefaultRefreshCookie(authResult);
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(authResult);
    }
}
