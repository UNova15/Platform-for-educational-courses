package kira.infrastructure.accesstoken;

import kira.infrastructure.api.AccessTokenGenerator;
import kira.infrastructure.api.TokenPayload;
import kira.infrastructure.exceptions.TokenDecodeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class JwtTokenProcessor implements AccessTokenDecoder, AccessTokenGenerator {
    private final SecretKey key;
    private final long clockSkew;
    private final Duration expirationTime;

    public static JwtTokenProcessor createWithKey(String key, long clockSkew, Duration expirationTime) {
        return new JwtTokenProcessor(
                Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)), clockSkew, expirationTime);
    }

    @Override
    public TokenPayload parseAccessToken(String token) throws TokenDecodeException {
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
                    parsedToken.get("role", String.class));

        } catch (JwtException | IllegalArgumentException exception) {
            throw new TokenDecodeException();
        }
    }

    @Override
    public String generateAccessToken(long id, String login, String role) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("login", login)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime.toMillis()))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}
