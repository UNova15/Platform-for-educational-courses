package kira.user.application.ports.out.crypto;

import kira.user.domain.confirmsession.valueobject.ConfirmationCode;

public interface ConfirmCodeGenerationPort {
    ConfirmationCode generate();
}
