package kira.auth.domain.user;

import kira.auth.application.exceptions.AuthExceptionCode;
import common.domain.Id;
import common.exception.DomainValidationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.UserRole;

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
            throw new DomainValidationException(
                    AuthExceptionCode.ERROR_CREATING_USER_DOMAIN_MODEL, "Password or login cannot be empty");
        }
        return new User(null, login, password, role);
    }

    public static User restore(Id<User> id, String password, String login, UserRole role) {
        return new User(id, Login.restore(login), HashedPassword.restoreFromHash(password), role);
    }
}
