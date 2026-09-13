/*
package org.platform.platformforeducationalcourses.util.token;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;

import refactor.auth.application.exceptions.InvalidTokenException;
import refactor.user.domain.user.UserRole;
import refactor.user.application.ports.out.auth.model.TokenPayload;
import refactor.user.adapter.out.token.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    private final SecretKey accessTokenGenerationKey;

    @Autowired
    public TokenUtil(TokenProperties configuration) {
        this.accessTokenGenerationKey = Keys.hmacShaKeyFor(configuration.accessTokenGenerationKey().getBytes(StandardCharsets.UTF_8));
    }

    public boolean isValidToken(String token) {
        if (token == null || token.isBlank()) return false;

        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public TokenPayload parseToken(String token) {
        try {
            Claims parsedToken = Jwts.parser()
                    .verifyWith(accessTokenGenerationKey)
                    .clockSkewSeconds()
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new TokenPayload(
                    Long.parseLong(parsedToken.getSubject()),
                    parsedToken.get("login", String.class),
                    UserRole.valueOf(parsedToken.get("role", String.class)));

        } catch (JwtException | IllegalArgumentException exception) {
            throw new InvalidTokenException("Некорректный токен: %s".formatted(token));
        }
    }

    public long parseUserIdFromToken(String token) {
        return parseToken(token).userId();
    }
}
*/
