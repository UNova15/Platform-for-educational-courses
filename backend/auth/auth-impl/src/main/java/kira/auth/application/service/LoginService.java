package kira.auth.application.service;

import kira.auth.application.exceptions.AuthenticationException;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.LoginUseCase;
import kira.auth.application.ports.out.crypto.PasswordHasherPort;
import kira.auth.application.ports.out.persistance.user.UserLoadPort;
import kira.auth.domain.user.User;
import kira.auth.domain.user.valueobject.HashedPassword;
import kira.auth.domain.user.valueobject.Login;
import kira.auth.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoginService implements LoginUseCase {
    private final UserLoadPort userLoadPort;
    private final PasswordHasherPort passwordHasherPort;
    private final TokenPublisher tokenService;

    @Override
    public AuthTokens login(Login login, RawPassword password) {
        User user = userLoadPort.loadUserByLogin(login).orElseThrow(() -> new AuthenticationException(login.value()));

        HashedPassword hashedPassword = user.password();

        boolean isCorrectPassword = passwordHasherPort.matches(password, hashedPassword);

        if (!isCorrectPassword) {
            throw new AuthenticationException(login.value());
        }

        return tokenService.issueTokens(user.id(), user.login());
    }
}
