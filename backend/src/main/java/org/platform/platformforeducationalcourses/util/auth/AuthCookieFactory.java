package org.platform.platformforeducationalcourses.util.auth;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.dto.auth.TokenDto;
import org.platform.platformforeducationalcourses.properties.TokenProperties;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthCookieFactory {
    private final TokenProperties configuration;

    public ResponseCookie createDefaultRefreshCookie(TokenDto token) {
        return ResponseCookie.from("refreshToken", token.refreshToken())
                .httpOnly(true)
                .path("/auth/refresh")
                .secure(true)
                .maxAge(configuration.refreshTtl())
                .build();
    }
}
