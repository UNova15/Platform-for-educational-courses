package org.platform.platformforeducationalcourses.persistance.repository.jdbc;

import java.util.Optional;
import org.platform.platformforeducationalcourses.persistance.entity.security.RefreshTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByToken(String token);

    Optional<RefreshTokenEntity> findByTokenAndUserId(String token, long userId);
}
