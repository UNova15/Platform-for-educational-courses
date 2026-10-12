package kira.auth.application.service;

import common.domain.Id;
import common.exception.named.ResourceAlreadyExist;
import kira.auth.application.exceptions.AuthResourceName;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.RegistrationUseCase;
import kira.auth.application.ports.out.crypto.PasswordHasherPort;
import kira.auth.application.ports.out.persistance.user.UserLoadPort;
import kira.auth.application.ports.out.persistance.user.UserSavePort;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class RegistrationService implements RegistrationUseCase {
    private final UserLoadPort loadPort;
    private final UserSavePort savePort;

    private final PasswordHasherPort passwordHasher;
    private final TokenPublisher tokenService;

    @Override
    @Transactional
    public AuthTokens registration(Login login, RawPassword password) {
        if (loadPort.isExistUserByLogin(login)) {
            throw new ResourceAlreadyExist(AuthResourceName.USER).withContext("login", login.value());
        }

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);
        User user = User.create(login, hashedPassword);

        Id<User> userId = savePort.save(user).id();

        return tokenService.issueTokens(userId, login);
    }
}
