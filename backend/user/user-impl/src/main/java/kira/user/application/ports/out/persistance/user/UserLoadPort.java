package kira.user.application.ports.out.persistance.user;

import common.domain.entity.Id;
import java.util.Optional;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Username;

public interface UserLoadPort {
    boolean isExistsUserByLogin(Username username);

    Optional<User> loadUserByLogin(Username username);

    Optional<User> loadUserById(Id<User> id);
}
