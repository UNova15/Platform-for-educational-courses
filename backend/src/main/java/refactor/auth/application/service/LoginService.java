package refactor.auth.application.service;

import lombok.RequiredArgsConstructor;
import refactor.auth.application.ports.out.crypto.PasswordHasherPort;
import refactor.auth.domain.user.valueobject.HashedPassword;
import refactor.auth.domain.user.valueobject.Login;
import refactor.auth.domain.user.valueobject.RawPassword;
import refactor.auth.application.exceptions.BadCredentialsException;
import org.springframework.stereotype.Service;
import refactor.auth.application.ports.in.AuthResult;
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
    public AuthResult login(Login login, RawPassword password) {

        User user = userLoadPort.loadUserByLogin(login).orElseThrow(() -> new BadCredentialsException(login));

        HashedPassword hashedPassword = user.password();

        boolean isCorrectPassword = passwordHasherPort.matches(password, hashedPassword);

        if (!isCorrectPassword) {
            throw new BadCredentialsException(login);
        }

        return tokenService.createTokens(user.id(), user.login(), user.role());
    }
}
