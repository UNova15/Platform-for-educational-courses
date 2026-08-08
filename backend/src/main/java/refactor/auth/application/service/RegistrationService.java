package refactor.auth.application.service;

import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refactor.auth.application.ports.in.usecase.PairOfTokens;
import refactor.auth.application.ports.in.usecase.RegistrationCommand;
import refactor.auth.application.ports.in.usecase.RegistrationUseCase;
import refactor.auth.application.ports.out.crypto.PasswordHasherPort;
import refactor.auth.application.ports.out.persistance.UserLoadPort;
import refactor.auth.application.ports.out.persistance.UserSavePort;
import refactor.auth.domain.user.HashedPassword;
import refactor.auth.domain.user.Login;
import refactor.auth.domain.user.RawPassword;
import refactor.auth.domain.user.User;

@Service
@RequiredArgsConstructor
class RegistrationService implements RegistrationUseCase {
    private final UserLoadPort userLoadPort;
    private final UserSavePort userSavePort;

    private final PasswordHasherPort passwordHasher;
    private final TokenService tokenService;

    @Transactional
    @Override
    public PairOfTokens registration(RegistrationCommand request) {
        if (userLoadPort.loadUserByLogin(request.login()).isPresent()) {
            throw new UserAlreadyExistException(request.login());
        }

        Login login = Login.of(request.login());
        RawPassword password = RawPassword.of(request.password());

        HashedPassword hashedPassword = passwordHasher.hashPassword(password);

        User user = User.createNew(login, hashedPassword, request.role());

        long userId = userSavePort.save(user).getId();

        return tokenService.createTokens(userId, request.login(), request.role());
    }
}
