package kira.user.adapter.out.persistence.user;

import common.domain.entity.Id;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Login;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    public User toDomain(UserEntity entity) {
        Login login = Login.restore(entity.login());
        HashedPassword password = HashedPassword.restoreFromHash(entity.password());

        return User.restore(Id.of(entity.id()), password, login);
    }

    public UserEntity toEntity(User user) {
        Long id = user.id() == null ? null : user.id().value();
        return new UserEntity(id, user.login().value(), user.password().value());
    }
}
