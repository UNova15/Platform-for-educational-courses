package org.platform.platformforeducationalcourses.domain.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

@Table(name = "users")
@Getter
@EqualsAndHashCode(of = "id")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
    private static final int MIN_PASSWORD_LENGTH = 8;

    @Id
    private final Long id;

    private String login;
    private String password;
    private final Role role;

    public static User createNew(String login, String password, Role role, PasswordEncoder passwordEncoder) {
        if (login == null
                || login.isBlank()
                || password == null
                || password.isBlank()
                || password.length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Incorrect data to create user");
        }

        String hashedPassword = passwordEncoder.encode(password);
        return new User(null, login, hashedPassword, role);
    }
}
