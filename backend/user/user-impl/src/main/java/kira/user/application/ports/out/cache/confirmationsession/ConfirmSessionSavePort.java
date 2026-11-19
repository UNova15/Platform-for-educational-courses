package kira.user.application.ports.out.cache.confirmationsession;

import kira.user.domain.confirmsession.ConfirmationSession;

public interface ConfirmSessionSavePort {
    void save(ConfirmationSession session);
}
