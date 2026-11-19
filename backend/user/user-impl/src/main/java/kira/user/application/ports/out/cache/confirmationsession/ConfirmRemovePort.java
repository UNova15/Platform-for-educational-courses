package kira.user.application.ports.out.cache.confirmationsession;

import kira.user.domain.confirmsession.ConfirmationSession;

public interface ConfirmRemovePort {
    void remove(ConfirmationSession session);
}
