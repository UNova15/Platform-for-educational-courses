package refactor.user.domain.user;

import static org.platform.platformforeducationalcourses.properties.constatnts.UserValidationConstants.MAX_PASSWORD_LENGTH;
import static org.platform.platformforeducationalcourses.properties.constatnts.UserValidationConstants.MIN_PASSWORD_LENGTH;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RawPassword {
    private final String password;

    public static RawPassword of(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Empty data to create raw password");
        }

        if (password.length() < MIN_PASSWORD_LENGTH || password.length() > MAX_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Incorrect password length: %d . Expected length: min: %d; max: %d"
                    .formatted(password.length(), MIN_PASSWORD_LENGTH, MAX_PASSWORD_LENGTH));
        }

        return new RawPassword(password);
    }
}
