package kira.auth.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import kira.auth.application.ports.in.AuthResult;

@Component
@AllArgsConstructor
class CookieFactory {
    private final CookieProperties cookieProperties;

    public ResponseCookie createDefaultRefreshCookie(AuthResult token) {
        return ResponseCookie.from("refreshToken", token.refreshToken())
                .httpOnly(true)
                .path("/kira/auth/refresh")
                .secure(true)
                .maxAge(cookieProperties.cookieTtl())
                .build();
    }
}
