package refactor.auth.application.service;

import lombok.RequiredArgsConstructor;
import refactor.auth.application.ports.out.crypto.PasswordHasherPort;
import refactor.auth.domain.user.HashedPassword;
import refactor.auth.domain.user.RawPassword;
import refactor.common.exception.auth.InvalidPasswordException;
import refactor.common.exception.notfound.UserNotFoundException;
import org.springframework.stereotype.Service;
import refactor.auth.application.ports.in.usecase.LoginCommand;
import refactor.auth.application.ports.in.usecase.PairOfTokens;
import refactor.auth.application.ports.in.usecase.LoginUseCase;
import refactor.auth.application.ports.out.persistance.UserLoadPort;
import refactor.auth.domain.user.User;

@Service
@RequiredArgsConstructor
class LoginService implements LoginUseCase {
    private final UserLoadPort userLoadPort;
    private final PasswordHasherPort passwordHasherPort;
    private final TokenService tokenService;

    @Override
    public PairOfTokens login(LoginCommand request) {
        User user = userLoadPort
                .loadUserByLogin(request.login())
                .orElseThrow(() -> new UserNotFoundException(request.login()));

        RawPassword inputPassword = RawPassword.of(request.password());
        HashedPassword hashedPassword = user.getPassword();

        boolean isCorrectPassword = passwordHasherPort.matches(inputPassword,hashedPassword);

        if(!isCorrectPassword){
            throw new InvalidPasswordException(request.login());
        }

        return tokenService.createTokens(user.getId(), user.getLogin(), user.getRole());
    }
}
