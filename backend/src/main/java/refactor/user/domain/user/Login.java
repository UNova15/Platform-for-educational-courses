package refactor.user.domain.user;

import static org.platform.platformforeducationalcourses.properties.constatnts.UserValidationConstants.MAX_LOGIN_LENGTH;
import static org.platform.platformforeducationalcourses.properties.constatnts.UserValidationConstants.MIN_LOGIN_LENGTH;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Login {
    private final String login;

    public static Login of(String login) {
        if (login == null || login.isBlank()) {
            throw new IllegalArgumentException("Empty data to create login");
        }
        if (login.length() < MIN_LOGIN_LENGTH || login.length() > MAX_LOGIN_LENGTH) {
            throw new IllegalArgumentException("Incorrect login length: %d . Expected length: min: %d; max: %d"
                    .formatted(login.length(), MIN_LOGIN_LENGTH, MAX_LOGIN_LENGTH));
        }
        return new Login(login);
    }

    public static Login restore(String login) {
        return new Login(login);
    }
}
