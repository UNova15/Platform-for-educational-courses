package kira.auth.application.ports.out.persistance;


import kira.auth.domain.user.User;

public interface UserSavePort {
    User save(User user);
}
