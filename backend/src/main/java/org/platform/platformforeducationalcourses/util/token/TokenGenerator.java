package org.platform.platformforeducationalcourses.util.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import javax.crypto.SecretKey;
import org.platform.platformforeducationalcourses.domain.user.UserRole;
import org.platform.platformforeducationalcourses.properties.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TokenGenerator {
    private final TokenProperties configuration;
    private final SecretKey key;

    @Autowired
    public TokenGenerator(TokenProperties tokenProperties) {
        this.configuration = tokenProperties;
        this.key = Keys.hmacShaKeyFor(configuration.key().getBytes(StandardCharsets.UTF_8));
    }

    public String generateJwtToken(long id, String login, UserRole role) {
        return generateToken(id, login, role, configuration.jwtTtl());
    }

    public String generateRefreshToken(long id, String login, UserRole role) {
        return generateToken(id, login, role, configuration.refreshTtl());
    }

    // TODO возможно устаревший класс для jwt
    private String generateToken(long id, String login, UserRole role, Duration expirationTime) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("login", login)
                .claim("role", role.toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime.toMillis()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
