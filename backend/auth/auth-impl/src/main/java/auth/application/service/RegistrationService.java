package auth.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import auth.application.exceptions.UserAlreadyExistException;
import refactor.auth.implemetnation.application.ports.in.AuthResult;
import refactor.auth.implemetnation.application.ports.in.RegistrationUseCase;
import refactor.auth.implemetnation.application.ports.out.crypto.PasswordHasherPort;
import refactor.auth.implemetnation.application.ports.out.persistance.UserLoadPort;
import refactor.auth.implemetnation.application.ports.out.persistance.UserSavePort;
import refactor.auth.implemetnation.domain.user.valueobject.HashedPassword;
import refactor.auth.implemetnation.domain.user.valueobject.Login;
import refactor.auth.implemetnation.domain.user.valueobject.RawPassword;
import refactor.auth.implemetnation.domain.user.User;
import refactor.auth.implemetnation.domain.user.valueobject.UserRole;
import refactor.common.domain.Id;

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
            throw new UserAlreadyExistException(login);
        }

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);
        User user = User.createNew(login, hashedPassword, role);

        Id<User> userId = userSavePort.save(user).id();

        return tokenService.createTokens(userId, login, role);
    }
}
