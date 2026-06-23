package org.platform.platformforeducationalcourses.domain.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.tokenutil.TokenHasher;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "refresh_tokens")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {
    @Id
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
