package kira.user.application.ports.out.cache.confirmsession;

import kira.user.domain.user.valueobject.Email;

public interface ConfirmSessionRemovePort {
    void remove(Email email);
}
