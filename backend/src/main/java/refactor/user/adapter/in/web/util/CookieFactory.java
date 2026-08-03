package refactor.user.adapter.in.web.util;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import refactor.user.application.ports.in.command.PairOfTokens;

@Component
@AllArgsConstructor
public class CookieFactory {
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
