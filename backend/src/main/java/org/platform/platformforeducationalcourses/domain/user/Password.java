package org.platform.platformforeducationalcourses.domain.user;

import static org.platform.platformforeducationalcourses.domain.constant.UserValidationConstants.MAX_PASSWORD_LENGTH;
import static org.platform.platformforeducationalcourses.domain.constant.UserValidationConstants.MIN_PASSWORD_LENGTH;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.platform.platformforeducationalcourses.security.hash.PasswordHasher;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Password {
    private final String password;

    public static Password create(String password, PasswordHasher hasher) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Empty data to create password");
        }

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Incorrect password length: %d . Expected length: min: %d; max: %d"
                    .formatted(password.length(), MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
        }

        String hashedPassword = hasher.hash(password);
        return new Password(hashedPassword);
    }

    public static Password restore(String password){
        return new Password(password);
    }
}
