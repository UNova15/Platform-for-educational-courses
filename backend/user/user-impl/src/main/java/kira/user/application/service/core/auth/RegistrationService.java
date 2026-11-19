package kira.user.application.service.core.auth;

import common.application.exception.ResourceAlreadyExist;
import common.domain.entity.Id;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.in.auth.RegistrationUseCase;
import kira.user.application.ports.out.cache.confirmationsession.ConfirmSessionSavePort;
import kira.user.application.ports.out.crypto.ConfirmCodeGenerationPort;
import kira.user.application.ports.out.crypto.PasswordHasherPort;
import kira.user.application.ports.out.event.EventPublisher;
import kira.user.application.ports.out.persistance.user.UserLoadPort;
import kira.user.application.ports.out.persistance.user.UserSavePort;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.confirmsession.ConfirmationSession;
import kira.user.domain.confirmsession.valueobject.ConfirmationCode;
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

    // TODO план:
    // 1) проверка уникальности логина, почты
    // 2) создание попытки регистрации
    // 3) создание события регистрации, сохранение в кеш попытки

    // 4) создание сервиса проверки кода подтверждения
    // 5) создание эндпоинта для принятия кода
    // 6) если код верный - сохранить в бд запись, генерация JWT+Refresh, удаление из кеша записи

    @Override
    public void registration(Username username, Email email, RawPassword password) {
        if (userLoadPort.isExistsUserByUsername(username)) {
            throw new ResourceAlreadyExist(AuthAggregateType.USER).withContext("username", username.value());
        }
        if (userLoadPort.isExistsUserByEmail(email)) {
            throw new ResourceAlreadyExist(AuthAggregateType.USER).withContext("email", email.value());
        }

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);
        ConfirmationCode code = confirmCodeGenerator.generate();

        var session = ConfirmationSession.create(username, email, code, hashedPassword);
        confirmSavePort.save(session);

        eventPublisher.publish(new RegistrationEvent(email.value(), code.value()));
    }

    @Transactional
    @Override
    public AuthTokens confirmCode(Email email, ConfirmationCode code) {
        var confirmSession = codeChecker.verifyAndConsume(email, code);
        var user = User.create(confirmSession.username(), confirmSession.email(), confirmSession.hashedPassword());

        Id<User> userId = userSavePort.save(user).id();
        return tokenPublisher.issueTokens(userId, user.username());
    }
}
