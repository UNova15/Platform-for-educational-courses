package org.platform.platformforeducationalcourses.persistance.entity.security;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.platform.platformforeducationalcourses.security.entity.RefreshToken;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "refresh_tokens")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshTokenEntity {
    @Id
    private final Long id;

    private final Long userId;
    private final String token;

    public static RefreshTokenEntity withToken(RefreshToken token) {
        return new RefreshTokenEntity(token.getId(), token.getUserId(), token.getToken());
    }
}
