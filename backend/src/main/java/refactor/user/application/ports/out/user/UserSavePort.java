package refactor.user.application.ports.out.user;

import refactor.user.domain.user.User;

public interface UserSavePort {
    User save(User user);
}
