package kira.user.domain.user;

import common.domain.entity.Id;
import common.domain.exception.DomainValidationException;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Username;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private final Id<User> id;
    private Username username;
    private Email email;
    private HashedPassword password;

    public static User create(Username username, Email email, HashedPassword password) {
        if (username == null || password == null || email == null) {
            throw new DomainValidationException("Empty data to create user", AuthAggregateType.USER);
        }
        return new User(null, username, email, password);
    }

    public static User restore(Id<User> id, Username username, Email email, HashedPassword password) {
        return new User(id, username, email, password);
    }
}
