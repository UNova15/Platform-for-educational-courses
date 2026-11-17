package kira.auth.application.ports.out.persistance.user;

import kira.auth.domain.user.User;

public interface UserSavePort {
    User save(User user);
}
