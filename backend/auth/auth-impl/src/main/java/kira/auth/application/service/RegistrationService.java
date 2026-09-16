package kira.auth.application.service;

import kira.auth.application.exceptions.AuthExceptionCode;
import kira.auth.application.ports.in.AuthResult;
import kira.auth.application.ports.in.RegistrationUseCase;
import kira.auth.application.ports.out.crypto.PasswordHasherPort;
import kira.auth.application.ports.out.persistance.UserLoadPort;
import kira.auth.application.ports.out.persistance.UserSavePort;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import kira.auth.domain.user.valueobject.UserRole;
import common.domain.Id;
import common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class RegistrationService implements RegistrationUseCase {
    private final UserLoadPort userLoadPort;
    private final UserSavePort userSavePort;

    private final PasswordHasherPort passwordHasher;
    private final TokenService tokenService;

    @Override
    @Transactional
    public AuthResult registration(Login login, RawPassword password, UserRole role) {
        if (userLoadPort.isExistUserByLogin(login)) {
            throw new ResourceNotFoundException(AuthExceptionCode.USER_NOT_FOUND_EXCEPTION,User.class,login);
        }

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);
        User user = User.createNew(login, hashedPassword, role);

        Id<User> userId = userSavePort.save(user).id();

        return tokenService.createTokens(userId, login, role);
    }
}
