package kira.user.application.ports.out.cache.confirmsession;

import java.util.Optional;
import kira.user.domain.code.ConfirmationSession;
import kira.user.domain.user.valueobject.Email;

public interface ConfirmSessionLoadPort {
    Optional<ConfirmationSession> load(Email email);
}
