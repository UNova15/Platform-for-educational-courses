package kira.auth.domain.user;

import common.domain.Id;
import common.exception.core.DomainValidationException;
import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.Login;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final Id<User> id;
    private Login login;
    private HashedPassword password;

    public static User create(Login login, HashedPassword password) {
        if (login == null || password == null) {
            throw new DomainValidationException("Password or login cannot be empty");
        }
        return new User(null, login, password);
    }

    public static User restore(Id<User> id, HashedPassword password, Login login) {
        return new User(id, login, password);
    }
}
