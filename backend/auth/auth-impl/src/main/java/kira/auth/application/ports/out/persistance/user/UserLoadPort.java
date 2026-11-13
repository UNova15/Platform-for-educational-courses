package kira.auth.application.ports.out.persistance.user;

import common.domain.entity.Id;
import java.util.Optional;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;

public interface UserLoadPort {
    boolean isExistsUserByLogin(Login login);

    Optional<User> loadUserByLogin(Login login);

    Optional<User> loadUserById(Id<User> id);
}
