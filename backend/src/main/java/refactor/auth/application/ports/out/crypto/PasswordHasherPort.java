package refactor.auth.application.ports.out.crypto;

import refactor.auth.domain.user.HashedPassword;
import refactor.auth.domain.user.RawPassword;

public interface PasswordHasherPort {
    HashedPassword hashPassword(RawPassword password);

    boolean matches(RawPassword rawPassword, HashedPassword hashedPassword);
}
