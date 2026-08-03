package refactor.user.adapter.out.persistence.user;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

interface OrmUserRepository extends CrudRepository<UserEntity, Long> {
    Optional<UserEntity> findByLogin(String login);
}
