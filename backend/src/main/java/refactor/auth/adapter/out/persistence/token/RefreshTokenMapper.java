package refactor.auth.adapter.out.persistence.token;

import org.springframework.stereotype.Component;
import refactor.auth.domain.token.RefreshToken;

@Component
class RefreshTokenMapper {

    public RefreshToken toDomain(RefreshTokenEntity entity) {
        return RefreshToken.restore(entity.getId(), entity.getUserId(), entity.getToken());
    }

    public RefreshTokenEntity toEntity(RefreshToken token) {
        return new RefreshTokenEntity(
                token.getId(), token.getUserId(), token.getToken().getHashedToken());
    }
}
