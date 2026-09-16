package kira.auth.application.service;

import kira.auth.application.ports.in.AuthResult;
import kira.auth.application.ports.in.LoginUseCase;
import kira.auth.application.ports.out.crypto.PasswordHasherPort;
import kira.auth.application.ports.out.persistance.UserLoadPort;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import kira.auth.application.exceptions.BadCredentialsException;
import org.springframework.stereotype.Service;

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
