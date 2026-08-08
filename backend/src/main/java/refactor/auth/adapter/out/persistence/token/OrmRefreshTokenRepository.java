package refactor.auth.adapter.out.persistence.token;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

interface OrmRefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByTokenAndUserId(String token, long userId);
}
