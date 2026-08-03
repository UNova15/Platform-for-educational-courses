package refactor.user.adapter.out.token.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import refactor.user.application.ports.out.token.AccessTokenGeneratePort;
import refactor.user.domain.user.UserRole;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenGenerator implements AccessTokenGeneratePort {
    private final Duration expirationTime;
    private final SecretKey key;

    public static JwtTokenGenerator createWithKey(Duration expirationTime, String key) {
        return new JwtTokenGenerator(expirationTime, Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public String generateAccessToken(long id, String login, UserRole role) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("login", login)
                .claim("role", role.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime.toMillis()))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}
