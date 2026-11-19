package kira.user.application.service.core.auth;

import common.application.exception.ResourceNotFoundException;
import kira.user.application.exceptions.ConfirmCodeException;
import kira.user.application.ports.out.cache.confirmationsession.ConfirmRemovePort;
import kira.user.application.ports.out.cache.confirmationsession.ConfirmSessionLoadPort;
import kira.user.application.ports.out.cache.confirmationsession.ConfirmSessionSavePort;
import kira.user.application.ports.out.crypto.ConfirmCodeGenerationPort;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.confirmsession.ConfirmationSession;
import kira.user.domain.confirmsession.valueobject.ConfirmationCode;
import kira.user.domain.user.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodeChecker {
    private final ConfirmSessionLoadPort confirmLoadPort;
    private final ConfirmRemovePort confirmRemovePort;
    private final ConfirmSessionSavePort confirmSessionSavePort;
    private final ConfirmCodeGenerationPort confirmCodeGenerator;

    public ConfirmationSession verifyAndConsume(Email email, ConfirmationCode code) {
        var confirmSession = confirmLoadPort
                .load(email)
                .orElseThrow(() -> new ResourceNotFoundException(AuthAggregateType.CONFIRMATION_SESSION)
                        .withContext("email", email.value()));

        return switch (confirmSession.verifyCode(code)) {
            case VERIFIED -> {
                confirmRemovePort.remove(confirmSession);
                yield confirmSession;
            }
            case INVALID_CODE -> {
                confirmSessionSavePort.save(confirmSession);
                throw new ConfirmCodeException();
            }
            case ATTEMPTS_EXHAUSTED -> {
                confirmRemovePort.remove(confirmSession);
                throw new ConfirmCodeException("Limit attempts has been reached");
            }
        };
    }
}
