package kira.auth.adapter.out.persistence.user;

import common.domain.Id;
import kira.auth.domain.user.User;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    public User toDomain(UserEntity entity) {
        return User.restore(Id.of(entity.getId()), entity.getPassword(), entity.getLogin());
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(
                user.id().value(), user.login().value(), user.password().value());
    }
}
