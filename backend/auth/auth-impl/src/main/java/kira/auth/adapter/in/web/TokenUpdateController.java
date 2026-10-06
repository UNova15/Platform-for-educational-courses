package kira.auth.adapter.in.web;

import common.domain.Result;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.UpdateTokenUseCase;
import kira.auth.domain.token.valueobject.RawRefreshToken;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String refreshToken) {
        RawRefreshToken token = RawRefreshToken.of(refreshToken);

        Result<AuthTokens, UpdateTokenUseCase.Error> authTokens = updateTokenUseCase.updateTokens(token);

        return authTokens.fold(
                tokens -> ResponseEntity.ok()
                        .header(
                                HttpHeaders.SET_COOKIE,
                                cookieFactory.createDefaultRefreshCookie(tokens).toString())
                        .body(tokens),
                failure -> switch (failure) {
                    case INVALID_REFRESH_TOKEN ->
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(failure);
                });
    }
}
