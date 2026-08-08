package refactor.auth.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class HashedPassword {
    private final String hashedPassword;

    public static HashedPassword of(String hashedPassword) {
        if (hashedPassword == null || hashedPassword.isBlank()) {
            throw new IllegalArgumentException("Hashed password cannot be empty");
        }

        return new HashedPassword(hashedPassword);
    }

    public static HashedPassword restoreFromHash(String hashedPassword) {
        return new HashedPassword(hashedPassword);
    }
}
