package refactor.auth.adapter.out.persistence.user;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.auth.application.ports.out.persistance.UserLoadPort;
import refactor.auth.application.ports.out.persistance.UserSavePort;
import refactor.auth.domain.user.User;

@Repository
@AllArgsConstructor
class UserRepositoryAdapter implements UserSavePort, UserLoadPort {
    private final OrmUserRepository repository;
    private final UserMapper mapper;

    @Override
    public Optional<User> loadUserByLogin(String login) {
        return repository.findByLogin(login).map(mapper::toDomain);
    }

    @Override
    public Optional<User> loadUserById(long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        UserEntity savedUser = repository.save(entity);
        return mapper.toDomain(savedUser);
    }
}
