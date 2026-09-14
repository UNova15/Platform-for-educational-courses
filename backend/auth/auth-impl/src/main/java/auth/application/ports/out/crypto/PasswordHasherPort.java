package auth.application.ports.out.crypto;

import refactor.auth.implemetnation.domain.user.valueobject.HashedPassword;
import refactor.auth.implemetnation.domain.user.valueobject.RawPassword;

public interface PasswordHasherPort {
    HashedPassword hashPassword(RawPassword password);

    boolean matches(RawPassword rawPassword, HashedPassword hashedPassword);
}
