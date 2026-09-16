package kira.auth.adapter.out.persistence.user;

import kira.auth.domain.user.User;
import common.domain.Id;
import org.springframework.stereotype.Component;

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
