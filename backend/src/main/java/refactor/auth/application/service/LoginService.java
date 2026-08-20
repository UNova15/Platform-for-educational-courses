package refactor.auth.application.service;

import lombok.RequiredArgsConstructor;
import refactor.auth.application.ports.out.crypto.PasswordHasherPort;
import refactor.auth.domain.user.HashedPassword;
import refactor.auth.domain.user.RawPassword;
import refactor.common.exception.auth.InvalidPasswordException;
import refactor.common.exception.domain.UserNotFoundException;
import org.springframework.stereotype.Service;
import refactor.auth.application.ports.in.LoginCommand;
import refactor.auth.application.ports.in.PairOfTokens;
import refactor.auth.application.ports.in.LoginUseCase;
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
        HashedPassword hashedPassword = user.password();

        boolean isCorrectPassword = passwordHasherPort.matches(inputPassword,hashedPassword);

        if(!isCorrectPassword){
            throw new InvalidPasswordException(request.login());
        }

        return tokenService.createTokens(user.id(), user.login().value(), user.role());
    }
}
