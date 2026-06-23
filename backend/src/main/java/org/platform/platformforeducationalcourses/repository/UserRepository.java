package org.platform.platformforeducationalcourses.repository;

import org.platform.platformforeducationalcourses.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByLogin (String login);
}
