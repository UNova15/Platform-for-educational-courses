package kira.user.adapter.out.persistence.user;

import common.domain.entity.Id;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Username;
import org.springframework.stereotype.Component;

@Component
class UserMapper {

    public User toDomain(UserEntity entity) {
        var username = Username.restore(entity.username());
        var password = HashedPassword.restoreFromHash(entity.password());
        var email = Email.restore(entity.email());

        return User.restore(Id.of(entity.id()), username, email, password);
    }

    public UserEntity toEntity(User user) {
        Long id = user.id() == null ? null : user.id().value();

        return new UserEntity(
                id,
                user.username().value(),
                user.email().value(),
                user.password().value());
    }
}
