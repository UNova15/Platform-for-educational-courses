package kira.user.adapter.out.persistence.user;

import org.springframework.data.repository.CrudRepository;

interface UserRepository extends CrudRepository<UserEntity, Long> {
    boolean existsByLogin(String login);

    boolean existsByEmail(String email);
}
