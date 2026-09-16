package kira.auth.application.ports.out.crypto;


import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.RawPassword;

public interface PasswordHasherPort {
    HashedPassword hashPassword(RawPassword password);

    boolean matches(RawPassword rawPassword, HashedPassword hashedPassword);
}
