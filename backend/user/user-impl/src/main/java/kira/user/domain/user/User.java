package kira.user.domain.user;

import common.domain.entity.Id;
import common.domain.exception.DomainValidationException;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Login;
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
    private Email email;
    private HashedPassword password;

    public static User create(Login login, Email email, HashedPassword password) {
        if (login == null || password == null || email == null) {
            throw new DomainValidationException("Empty data to create user", AuthAggregateType.USER);
        }
        return new User(null, login, email, password);
    }

    public static User restore(Id<User> id, Login login, Email email, HashedPassword password) {
        return new User(id, login, email, password);
    }
}
