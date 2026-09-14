package auth.adapter.out.persistence.token;

import org.springframework.stereotype.Component;
import refactor.auth.implemetnation.domain.token.RefreshToken;
import refactor.auth.implemetnation.domain.token.valueobject.HashedRefreshToken;
import refactor.common.domain.Id;

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
