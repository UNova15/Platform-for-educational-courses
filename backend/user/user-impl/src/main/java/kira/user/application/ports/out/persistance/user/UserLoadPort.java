package kira.user.application.ports.out.persistance.user;

import common.domain.entity.Id;
import java.util.Optional;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.Username;

public interface UserLoadPort {
    boolean isExistsUserByUsername(Username username);

    boolean isExistsUserByEmail(Email email);

    Optional<User> loadUserByEmail(Email email);

    Optional<User> loadUserById(Id<User> id);
}
