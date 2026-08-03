package refactor.user.adapter.out.persistence.user;

import org.springframework.stereotype.Component;
import refactor.user.domain.user.User;

@Component
class UserMapper {

    public User toDomain(UserEntity entity) {
        return User.restore(entity.getId(), entity.getPassword(), entity.getLogin(), entity.getRole());
    }

    public UserEntity toEntity(User user) {
        return new UserEntity(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
    }
}
