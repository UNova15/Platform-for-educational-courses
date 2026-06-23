package org.platform.platformforeducationalcourses.repository;

import org.platform.platformforeducationalcourses.domain.security.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByTokenAndUserId(String token, long userId);;
}
