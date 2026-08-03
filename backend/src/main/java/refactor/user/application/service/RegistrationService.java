package refactor.user.application.service;

import lombok.RequiredArgsConstructor;
import org.platform.platformforeducationalcourses.exception.UserAlreadyExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import refactor.user.application.ports.in.command.PairOfTokens;
import refactor.user.application.ports.in.command.RegistrationCommand;
import refactor.user.application.ports.in.usecase.RegistrationUseCase;
import refactor.user.application.ports.out.user.PasswordHasherPort;
import refactor.user.application.ports.out.user.UserLoadPort;
import refactor.user.application.ports.out.user.UserSavePort;
import refactor.user.domain.user.HashedPassword;
import refactor.user.domain.user.Login;
import refactor.user.domain.user.RawPassword;
import refactor.user.domain.user.User;

@Service
@RequiredArgsConstructor
public class RegistrationService implements RegistrationUseCase {
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

        HashedPassword hashedPassword = passwordHasher.hash(password);

        User user = User.createNew(login, hashedPassword, request.role());

        long userId = userSavePort.save(user).getId();

        return tokenService.createTokens(userId, request.login(), request.role());
    }
}
