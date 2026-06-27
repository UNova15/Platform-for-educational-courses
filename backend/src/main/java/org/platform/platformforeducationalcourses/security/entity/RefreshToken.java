package org.platform.platformforeducationalcourses.security.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.platform.platformforeducationalcourses.security.hash.TokenHasher;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
    private final Long id;

    private final Long userId;
    private final String token;

    public static RefreshToken createNew(long userId, String token, TokenHasher hasher) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Incorrect token for user: %d".formatted(userId));
        }

        String hashedToken = hasher.hash(token);
        return new RefreshToken(null, userId, hashedToken);
    }
}
