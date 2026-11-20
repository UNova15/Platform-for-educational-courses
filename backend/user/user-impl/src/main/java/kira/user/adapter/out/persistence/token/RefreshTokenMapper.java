package kira.user.adapter.out.persistence.token;

import common.domain.entity.Id;
import kira.user.domain.token.RefreshToken;
import kira.user.domain.token.valueobject.HashedRefreshToken;
import org.springframework.stereotype.Component;

@Component
class RefreshTokenMapper {

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        HashedRefreshToken token = HashedRefreshToken.of(entity.token());
        return RefreshToken.restore(Id.of(entity.id()), Id.of(entity.userId()), token);
    }

    public RefreshTokenEntity toEntity(RefreshToken refreshToken) {
        Long id = refreshToken.id() == null ? null : refreshToken.id().value();
        return new RefreshTokenEntity(
                id, refreshToken.userId().value(), refreshToken.token().value());
    }
}
