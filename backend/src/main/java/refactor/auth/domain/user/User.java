package refactor.auth.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final Long id;
    private Login login;
    private HashedPassword password;
    private final UserRole role;

    public static User createNew(Login login, HashedPassword password, UserRole role) {
        if (login == null || password == null) {
            throw new IllegalArgumentException("Empty data to create user");
        }
        return new User(null, login, password, role);
    }

    public static User restore(long id, String password, String login, UserRole role) {
        return new User(id, Login.restore(login), HashedPassword.restoreFromHash(password), role);
    }
}
