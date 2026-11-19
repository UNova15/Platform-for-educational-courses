package kira.user.application.service.core.auth;

import common.application.exception.ResourceAlreadyExist;
import common.application.exception.ResourceNotFoundException;
import common.domain.entity.Id;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.in.auth.RegistrationUseCase;
import kira.user.application.ports.out.cache.confirmationsession.ConfirmSessionLoadPort;
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
import kira.user.domain.user.valueobject.Username;
import kira.user.domain.user.valueobject.RawPassword;
import kira.user.event.RegistrationEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class RegistrationService implements RegistrationUseCase {
    private final UserLoadPort userLoadPort;
    private final UserSavePort userSavePort;

    private final PasswordHasherPort passwordHasher;
    private final TokenPublisher tokenPublisher;
    private final EventPublisher eventPublisher;
    private final ConfirmCodeGenerationPort confirmCodeGenerator;

    private final ConfirmSessionSavePort confirmSavePort;
    private final CodeChecker codeChecker;

    private final ConfirmSessionLoadPort confirmSessionLoadPort;

    /*
    При регистрации сбрасывается предыдущая попытка регистрации данного пользователя (пользователя с такой же почтой),
    если он не завершил регистрацию (В кеш перезаписываются данные новой попытки).
    Потенциальная уязвимость: пользователь может не заканчивая регистрацию (без ввода кода подтверждения) заново
    инициировать процесс регистрации, тем самым спамить сообщениями в обход общего механизма задержек. В данной итерации
    разработки упрощено, в дальнейшем для исправления необходимо проверять наличие уже существующей попытки и проверять
    в ней время последней отправки сообщения.
     */

    @Override
    public void registration(Username username, Email email, RawPassword password) {
        if (userLoadPort.isExistsUserByUsername(username)) {
            throw new ResourceAlreadyExist(AuthAggregateType.USER).withContext("username", username.value());
        }
        if (userLoadPort.isExistsUserByEmail(email)) {
            throw new ResourceAlreadyExist(AuthAggregateType.USER).withContext("email", email.value());
        }

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);
        Code code = confirmCodeGenerator.generate();

        var session = ConfirmationSession.create(username, email, code, hashedPassword);
        confirmSavePort.save(session);

        eventPublisher.publish(new RegistrationEvent(email.value(), code.value()));
    }

    @Transactional
    @Override
    public AuthTokens confirmCode(Email email, Code code) {
        var confirmSession = codeChecker.verifyAndConsume(email, code);
        var user = User.create(confirmSession.username(), confirmSession.email(), confirmSession.hashedPassword());

        Id<User> userId = userSavePort.save(user).id();
        return tokenPublisher.issueTokens(userId, user.username());
    }

    @Override
    public void repeatSendingCode(Email email) {
        var confirmSession = confirmSessionLoadPort
                .load(email)
                .orElseThrow(() -> new ResourceNotFoundException(AuthAggregateType.CONFIRMATION_SESSION));

        var newCode = confirmCodeGenerator.generate();

        confirmSession.updateCodeForResend(newCode);

        confirmSavePort.save(confirmSession);
        eventPublisher.publish(new RegistrationEvent(email.value(), newCode.value()));
    }
}
