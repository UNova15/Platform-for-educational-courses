package kira.user.application.service.core.auth;

import common.application.exception.ResourceNotFoundException;
import kira.user.application.exceptions.ConfirmCodeException;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionLoadPort;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionRemovePort;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionSavePort;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.code.ConfirmationSession;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodeChecker {
    private final ConfirmSessionLoadPort confirmLoadPort;
    private final ConfirmSessionRemovePort confirmRemovePort;
    private final ConfirmSessionSavePort confirmSessionSavePort;

    public ConfirmationSession verifyAndConsume(Email email, Code code) {
        var confirmSession = confirmLoadPort.load(email).orElseThrow(() -> new ResourceNotFoundException(
                        AuthAggregateType.CONFIRMATION_SESSION)
                .withContext("email", email.value()));

        return switch (confirmSession.verifyCode(code)) {
            case VERIFIED -> {
                confirmRemovePort.remove(confirmSession.email());
                yield confirmSession;
            }
            case INVALID_CODE -> {
                confirmSessionSavePort.save(confirmSession);
                throw new ConfirmCodeException();
            }
            case ATTEMPTS_EXHAUSTED -> {
                confirmRemovePort.remove(confirmSession.email());
                throw new ConfirmCodeException("Limit attempts has been reached");
            }
        };
    }
}
