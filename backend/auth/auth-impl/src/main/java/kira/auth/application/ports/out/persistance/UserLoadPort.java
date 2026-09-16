package kira.auth.application.ports.out.persistance;

import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.Login;
import common.domain.Id;

import java.util.Optional;

public interface UserLoadPort {
    boolean isExistUserByLogin(Login login);

    Optional<User> loadUserByLogin(Login login);

    Optional<User> loadUserById(Id<User> id);
}
