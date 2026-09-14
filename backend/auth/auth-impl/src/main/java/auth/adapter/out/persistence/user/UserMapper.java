package auth.adapter.out.persistence.user;

import org.springframework.stereotype.Component;
import refactor.auth.implemetnation.domain.user.User;
import refactor.common.domain.Id;

@Component
class UserMapper {

    public User toDomain(UserEntity entity) {
        return User.restore(Id.of(entity.getId()), entity.getPassword(), entity.getLogin(), entity.getRole());
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.id().value(), user.login().value(), user.password().value(), user.role());
    }
}
