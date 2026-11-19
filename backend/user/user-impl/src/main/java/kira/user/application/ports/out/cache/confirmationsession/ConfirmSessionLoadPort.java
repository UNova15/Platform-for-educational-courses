package kira.user.application.ports.out.cache.confirmationsession;

import kira.user.domain.code.ConfirmationSession;
import kira.user.domain.user.valueobject.Email;

import java.util.Optional;

public interface ConfirmSessionLoadPort {
    Optional<ConfirmationSession> load(Email email);
}
