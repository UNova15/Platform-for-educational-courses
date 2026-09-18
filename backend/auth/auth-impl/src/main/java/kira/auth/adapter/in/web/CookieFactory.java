package kira.auth.adapter.in.web;

import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;
import kira.auth.application.ports.in.AuthResult;

@Configuration
@EnableConfigurationProperties(CookieProperties.class)
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
