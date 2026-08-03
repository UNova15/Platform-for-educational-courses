package refactor.user.application.ports.out.user;

import refactor.user.domain.user.HashedPassword;
import refactor.user.domain.user.RawPassword;

public interface PasswordHasherPort {
    HashedPassword hash(RawPassword password);
}
