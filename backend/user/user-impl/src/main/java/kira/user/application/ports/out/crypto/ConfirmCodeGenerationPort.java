package kira.user.application.ports.out.crypto;

import kira.user.domain.code.valueobject.Code;

public interface ConfirmCodeGenerationPort {
    Code generate();
}
