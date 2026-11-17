package kira.user.application.service.core.auth;

import kira.user.application.exceptions.AuthenticationException;
import kira.user.application.ports.in.auth.AuthTokens;
import kira.user.application.ports.in.auth.LoginUseCase;
import kira.user.application.ports.out.crypto.PasswordHasherPort;
import kira.user.application.ports.out.persistance.user.UserLoadPort;
import kira.user.domain.user.User;
import kira.user.domain.user.valueobject.HashedPassword;
import kira.user.domain.user.valueobject.Username;
import kira.user.domain.user.valueobject.RawPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoginService implements LoginUseCase {
    private final UserLoadPort userLoadPort;
    private final PasswordHasherPort passwordHasherPort;
    private final TokenPublisher tokenService;

    @Override
    public AuthTokens login(Username username, RawPassword password) {
        User user = userLoadPort.loadUserByLogin(username).orElseThrow(() -> new AuthenticationException(username.value()));

        HashedPassword hashedPassword = user.password();

        boolean isCorrectPassword = passwordHasherPort.matches(password, hashedPassword);

        if (!isCorrectPassword) {
            throw new AuthenticationException(username.value());
        }

        return tokenService.issueTokens(user.id(), user.username());
    }
}
