package kira.auth.adapter.out.persistence.user;

import common.domain.Id;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.Login;
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
