package refactor.auth.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import refactor.auth.application.ports.in.PairOfTokens;

@Component
@AllArgsConstructor
class CookieFactory {
    private final CookieProperties cookieProperties;

    public ResponseCookie createDefaultRefreshCookie(PairOfTokens token) {
        return ResponseCookie.from("refreshToken", token.refreshToken())
                .httpOnly(true)
                .path("/auth/refresh")
                .secure(true)
                .maxAge(cookieProperties.cookieTtl())
                .build();
    }
}
