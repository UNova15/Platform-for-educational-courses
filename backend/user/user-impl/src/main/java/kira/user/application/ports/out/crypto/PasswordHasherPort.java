package kira.user.application.ports.out.crypto;

import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.RawPassword;

public interface PasswordHasherPort {
    HashedPassword hashPassword(RawPassword password);

    boolean matches(RawPassword rawPassword, HashedPassword hashedPassword);
}
