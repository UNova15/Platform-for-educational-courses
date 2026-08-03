package refactor.user.adapter.out.token.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import refactor.common.exception.InvalidTokenException;
import refactor.user.application.ports.out.token.AccessTokenDecodePort;
import refactor.user.application.ports.out.token.model.TokenPayload;
import refactor.user.domain.user.UserRole;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenDecoder implements AccessTokenDecodePort {
    private final SecretKey key;
    private final long clockSkew;

    public static JwtTokenDecoder createWithKey(String key, long clockSkew) {
        return new JwtTokenDecoder(Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)), clockSkew);
    }

    @Override
    public TokenPayload parseToken(String token) throws InvalidTokenException {
        try {
            Claims parsedToken = Jwts.parser()
                    .verifyWith(key)
                    .clockSkewSeconds(clockSkew)
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
}
