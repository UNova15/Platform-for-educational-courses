package kira.user.application.service.core.auth;

import common.application.exception.ResourceNotFoundException;
import kira.user.application.ports.in.auth.ResendCodeUseCase;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionLoadPort;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionSavePort;
import kira.user.application.ports.out.crypto.ConfirmCodeGenerationPort;
import kira.user.application.ports.out.event.EventPublisher;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.code.ConfirmationSession;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.valueobject.Email;
import kira.user.event.CodeConfirmationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeResendService implements ResendCodeUseCase {
    private final ConfirmSessionLoadPort confirmSessionLoadPort;
    private final ConfirmCodeGenerationPort generationPort;
    private final EventPublisher eventPublisher;
    private final ConfirmSessionSavePort confirmSessionSavePort;

    @Override
    public void resend(Email email) {
        ConfirmationSession session = confirmSessionLoadPort
                .load(email)
                .orElseThrow(() -> new ResourceNotFoundException(AuthAggregateType.CONFIRMATION_SESSION)
                        .withContext("email", email.value()));

        Code newCode = generationPort.generate();
        session.updateCodeForResend(newCode);

        confirmSessionSavePort.save(session);
        eventPublisher.publish(new CodeConfirmationEvent(email.value(), newCode.value()));
    }
}
