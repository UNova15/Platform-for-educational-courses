package auth.application.ports.out.persistance;

import java.util.Optional;
import refactor.auth.implemetnation.domain.user.User;
import refactor.auth.implemetnation.domain.user.valueobject.Login;
import refactor.common.domain.Id;

public interface UserLoadPort {
    boolean isExistUserByLogin(Login login);

    Optional<User> loadUserByLogin(Login login);

    Optional<User> loadUserById(Id<User> id);
}
