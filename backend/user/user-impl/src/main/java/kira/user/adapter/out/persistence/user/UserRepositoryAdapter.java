package kira.user.adapter.out.persistence.user;

import common.domain.entity.Id;
import java.util.Optional;
import kira.user.application.ports.out.persistance.user.UserLoadPort;
import kira.user.application.ports.out.persistance.user.UserSavePort;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.Username;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
class UserRepositoryAdapter implements UserSavePort, UserLoadPort {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public boolean isExistsUserByUsername(Username username) {
        return repository.existsByUsername(username.value());
    }

    @Override
    public boolean isExistsUserByEmail(Email email) {
        return repository.existsByEmail(email.value());
    }

    @Override
    public Optional<User> loadUserByEmail(Email email) {
        Optional<UserEntity> entity = repository.findByEmail(email.value());
        return entity.map(mapper::toDomain);
    }

    @Override
    public Optional<User> loadUserById(Id<User> id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedUser = repository.save(entity);
        return mapper.toDomain(savedUser);
    }
}
