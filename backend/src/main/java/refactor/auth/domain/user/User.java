package refactor.auth.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import refactor.auth.domain.user.valueobject.HashedPassword;
import refactor.auth.domain.user.valueobject.Login;
import refactor.auth.domain.user.valueobject.UserRole;
import refactor.common.domain.Id;
import refactor.common.exception.DomainValidationException;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final Id<User> id;
    private Login login;
    private HashedPassword password;
    private final UserRole role;

    public static User createNew(Login login, HashedPassword password, UserRole role) {
        if (login == null || password == null) {
            throw new DomainValidationException("Empty login or password to create user");
        }
        return new User(null, login, password, role);
    }

    public static User restore(Id<User> id, String password, String login, UserRole role) {
        return new User(id, Login.restore(login), HashedPassword.restoreFromHash(password), role);
    }
}
