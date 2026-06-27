package org.platform.platformforeducationalcourses.persistance.repository.adapter;

import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.ports.persistance.UserRepository;
import org.platform.platformforeducationalcourses.domain.user.User;
import org.platform.platformforeducationalcourses.persistance.entity.user.UserEntity;
import org.platform.platformforeducationalcourses.persistance.repository.jdbc.DataUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserRepositoryAdapter implements UserRepository {
    private final DataUserRepository repository;

    @Override
    public Optional<User> findByLogin(String login) {
        Optional<UserEntity> entity = repository.findByLogin(login);
        return entity.map(value -> User.restore(value.getId(), value.getPassword(), value.getLogin(), value.getRole()));
    }

    @Override
    public User save(User user) {
        UserEntity entity = UserEntity.fromUser(user);
        UserEntity savedUser = repository.save(entity);
        return User.restore(savedUser.getId(), savedUser.getPassword(), savedUser.getLogin(), savedUser.getRole());
    }
}
