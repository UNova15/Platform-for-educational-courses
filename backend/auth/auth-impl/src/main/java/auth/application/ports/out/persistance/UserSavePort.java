package auth.application.ports.out.persistance;

import refactor.auth.implemetnation.domain.user.User;

public interface UserSavePort {
    User save(User user);
}
