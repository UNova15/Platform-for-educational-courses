package kira.user.application.service.core.auth;

import common.application.exception.ResourceAlreadyExist;
import common.domain.entity.Id;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.in.auth.RegistrationUseCase;
import kira.user.application.ports.out.crypto.PasswordHasherPort;
import kira.user.application.ports.out.persistance.user.UserLoadPort;
import kira.user.application.ports.out.persistance.user.UserSavePort;
import kira.user.domain.AuthAggregateType;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Login;
import kira.user.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class RegistrationService implements RegistrationUseCase {
    private final UserLoadPort loadPort;
    private final UserSavePort savePort;

    private final PasswordHasherPort passwordHasher;
    private final TokenPublisher tokenPublisher;

    @Override
    @Transactional
    public AuthTokens registration(Login login, RawPassword password) {
        if (loadPort.isExistsUserByLogin(login)) {
            throw new ResourceAlreadyExist(AuthAggregateType.USER).withContext("login", login.value());
        }

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);
        var user = User.create(login, hashedPassword);

        Id<User> userId = savePort.save(user).id();

        return tokenPublisher.issueTokens(userId, login);
    }
}
