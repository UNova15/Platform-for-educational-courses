package org.platform.platformforeducationalcourses.tokenutil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.platform.platformforeducationalcourses.properties.TokenProperties;
import org.platform.platformforeducationalcourses.domain.user.Role;
import org.platform.platformforeducationalcourses.dto.auth.ParsedToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
public class TokenUtil {
    private final SecretKey key;

    @Autowired
    public TokenUtil(TokenProperties configuration) {
        this.key = Keys.hmacShaKeyFor(configuration.key().getBytes(StandardCharsets.UTF_8));
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

    public ParsedToken parseToken(String token) {
        try {
            Claims parsedToken = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return new ParsedToken(
                    Long.parseLong(parsedToken.getSubject()),
                    parsedToken.get("login", String.class),
                    Role.valueOf(
                            parsedToken.get("role", String.class)
                    )
            );

        } catch (JwtException | IllegalArgumentException exception) {
            throw new BadCredentialsException("Некорректный токен: " + token);
        }
    }

    public long parseUserIdFromToken(String token) {
        return parseToken(token).userId();
    }
}
