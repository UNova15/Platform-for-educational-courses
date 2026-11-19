package kira.user.application.ports.out.persistance.user;

import common.domain.entity.Id;
import java.util.Optional;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Login;

public interface UserLoadPort {
    boolean isExistsUserByLogin(Login login);

    Optional<User> loadUserByLogin(Login login);

    Optional<User> loadUserById(Id<User> id);
}
