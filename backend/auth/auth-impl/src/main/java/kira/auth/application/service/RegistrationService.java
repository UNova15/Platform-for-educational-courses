package kira.auth.application.service;

import common.domain.Id;
import common.domain.Result;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.RegistrationUseCase;
import kira.auth.application.ports.out.crypto.PasswordHasherPort;
import kira.auth.application.ports.out.persistance.UserLoadPort;
import kira.auth.application.ports.out.persistance.UserSavePort;
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
    private final UserLoadPort userLoadPort;
    private final UserSavePort userSavePort;

    private final PasswordHasherPort passwordHasher;
    private final TokenPublisher tokenService;

    @Override
    @Transactional
    public Result<AuthTokens, Error> registration(Login login, RawPassword password) {
        if (userLoadPort.isExistUserByLogin(login)) {
            return Result.failure(Error.USER_ALREADY_EXISTS);
        }

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);
        User user = User.create(login, hashedPassword);

        Id<User> userId = userSavePort.save(user).id();

        return Result.success(tokenService.issueTokens(userId, login));
    }
}
