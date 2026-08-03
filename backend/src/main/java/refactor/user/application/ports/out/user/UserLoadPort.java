package refactor.user.application.ports.out.user;

import java.util.Optional;
import refactor.user.domain.user.User;

public interface UserLoadPort {
    Optional<User> loadUserByLogin(String login);

    Optional<User> loadUserById(long id);
}
