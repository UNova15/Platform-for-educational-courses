package kira.user.application.ports.out.cache.confirmsession;

import kira.user.domain.code.ConfirmationSession;

public interface ConfirmSessionSavePort {
    void save(ConfirmationSession session);
}
