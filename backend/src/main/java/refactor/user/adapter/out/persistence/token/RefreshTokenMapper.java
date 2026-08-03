package refactor.user.adapter.out.persistence.token;

import org.springframework.stereotype.Component;
import refactor.user.domain.token.RefreshToken;

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
