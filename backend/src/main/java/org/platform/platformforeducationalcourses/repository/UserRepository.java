package org.platform.platformforeducationalcourses.repository;

import java.util.Optional;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin(String login);
}
