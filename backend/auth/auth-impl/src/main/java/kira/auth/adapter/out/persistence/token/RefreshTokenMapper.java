package kira.auth.adapter.out.persistence.token;

import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;
import common.domain.Id;
import org.springframework.stereotype.Component;

@Component
class RefreshTokenMapper {

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        HashedRefreshToken token = HashedRefreshToken.restoreFromHash(entity.getToken());
        return RefreshToken.restore(Id.of(entity.getId()), Id.of(entity.getUserId()), token);
    }

    public RefreshTokenEntity toEntity(RefreshToken refreshToken) {
        return new RefreshTokenEntity(
                refreshToken.id().value(),
                refreshToken.userId().value(),
                refreshToken.token().value());
    }
}
