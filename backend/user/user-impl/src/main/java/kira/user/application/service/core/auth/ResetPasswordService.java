package kira.user.application.service.core.auth;

import common.application.exception.ResourceNotFoundException;
import kira.user.application.ports.in.auth.ResetPasswordUseCase;
import kira.user.application.ports.out.cache.confirmationsession.ConfirmSessionSavePort;
import kira.user.application.ports.out.crypto.ConfirmCodeGenerationPort;
import kira.user.application.ports.out.crypto.PasswordHasherPort;
import kira.user.application.ports.out.event.EventPublisher;
import kira.user.application.ports.out.persistance.user.UserLoadPort;
import kira.user.application.ports.out.persistance.user.UserSavePort;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.code.ConfirmationSession;
import kira.user.domain.code.valueobject.Code;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.Email;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.RawPassword;
import kira.user.event.CodeGeneratedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResetPasswordService implements ResetPasswordUseCase {
    private final UserLoadPort userLoadPort;
    private final UserSavePort userSavePort;

    private final ConfirmCodeGenerationPort generationPort;
    private final ConfirmSessionSavePort confirmSessionSavePort;

    private final PasswordHasherPort passwordHasherPort;
    private final EventPublisher eventPublisher;
    private final CodeChecker codeChecker;

    @Override
    public void resetPassword(Email email, RawPassword rawPassword) {
        User user = userLoadPort
                .loadUserByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AuthAggregateType.USER).withContext("email", email.value()));

        HashedPassword password = passwordHasherPort.hashPassword(rawPassword);
        Code code = generationPort.generate();

        var session = ConfirmationSession.create(user.username(), user.email(), code, password);
        confirmSessionSavePort.save(session);

        eventPublisher.publish(new CodeGeneratedEvent(user.email().value(), code.value()));
    }

    @Override
    public void confirmCode(Email email, Code inputCode) {
        User user = userLoadPort
                .loadUserByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(AuthAggregateType.USER).withContext("email", email.value()));

        ConfirmationSession session = codeChecker.verifyAndConsume(email, inputCode);

        user.resetPassword(session.hashedPassword());
        userSavePort.save(user);
    }
}
