package org.platform.platformforeducationalcourses.repository;

import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.security.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByTokenAndUserId(String token, long userId);
    ;
}
