package org.platform.platformforeducationalcourses.domain.ports.persistance;

import org.platform.platformforeducationalcourses.domain.user.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByLogin(String login);

    User save(User user);
}
