package kira.auth.adapter.in.web;

import kira.auth.application.ports.in.AuthTokens;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseCookie;

@Configuration
@EnableConfigurationProperties(CookieProperties.class)
@AllArgsConstructor
class CookieFactory {
    private final CookieProperties cookieProperties;

    public ResponseCookie createDefaultRefreshCookie(AuthTokens token) {
        return ResponseCookie.from("refreshToken", token.refreshToken())
                .httpOnly(true)
                .path("/kira/auth/refresh")
                .secure(true)
                .maxAge(cookieProperties.cookieTtl())
                .build();
    }
}
