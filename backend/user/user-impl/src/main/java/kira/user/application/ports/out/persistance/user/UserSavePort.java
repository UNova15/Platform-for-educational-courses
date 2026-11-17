package kira.user.application.ports.out.persistance.user;

import kira.user.domain.user.User;

public interface UserSavePort {
    User save(User user);
}
