package org.platform.platformforeducationalcourses.persistance.repository.jdbc;

import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.persistance.entity.user.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface DataUserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
