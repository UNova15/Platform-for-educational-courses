package org.platform.platformforeducationalcourses.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final Long id;

    @Getter(value = AccessLevel.NONE)
    private Login login;

    @Getter(value = AccessLevel.NONE)
    private Password password;

    private final UserRole role;

    public static User createNew(Login login, Password password, UserRole role) {
        if (login == null || password == null) {
            throw new IllegalArgumentException("Empty data to create user");
        }
        return new User(null, login, password, role);
    }

    public static User restore(long id, String password, String login, UserRole role) {
        return new User(id, Login.restore(login), Password.restore(password), role);
    }

    public String getLogin() {
        return login.getLogin();
    }

    public String getPassword() {
        return password.getPassword();
    }
}
