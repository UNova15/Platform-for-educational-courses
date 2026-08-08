package refactor.auth.application.ports.out.persistance;

import refactor.auth.domain.user.User;

public interface UserSavePort {
    User save(User user);
}
