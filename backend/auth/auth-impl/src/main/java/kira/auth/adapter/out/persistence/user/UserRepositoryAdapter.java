package kira.auth.adapter.out.persistence.user;

import java.util.Optional;

import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;
import common.domain.Id;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import kira.auth.application.ports.out.persistance.UserLoadPort;
import kira.auth.application.ports.out.persistance.UserSavePort;

@Repository
@AllArgsConstructor
class UserRepositoryAdapter implements UserSavePort, UserLoadPort {
    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public boolean isExistUserByLogin(Login login) {
        return repository.existsByLogin(login.value());
    }

    @Override
    public Optional<User> loadUserByLogin(Login login) {
        return repository.findByLogin(login.value()).map(mapper::toDomain);
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
