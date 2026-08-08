package refactor.auth.application.ports.out.persistance;

import java.util.Optional;
import refactor.auth.domain.user.User;

public interface UserLoadPort {
    Optional<User> loadUserByLogin(String login);

    Optional<User> loadUserById(long id);
}
