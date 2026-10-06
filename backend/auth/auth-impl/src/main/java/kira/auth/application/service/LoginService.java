package kira.auth.application.service;

import common.domain.Result;
import java.util.Optional;
import kira.auth.application.ports.in.AuthTokens;
import kira.auth.application.ports.in.LoginUseCase;
import kira.auth.application.ports.out.crypto.PasswordHasherPort;
import kira.auth.application.ports.out.persistance.UserLoadPort;
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
    public Result<AuthTokens, Error> login(Login login, RawPassword password) {
        Optional<User> loadedUser = userLoadPort.loadUserByLogin(login);

        if (loadedUser.isEmpty()) {
            return Result.failure(Error.INVALID_CREDENTIALS);
        }

        User user = loadedUser.get();
        HashedPassword hashedPassword = user.password();

        boolean isCorrectPassword = passwordHasherPort.matches(password, hashedPassword);

        if (!isCorrectPassword) {
            return Result.failure(Error.INVALID_CREDENTIALS);
        }

        return Result.success(tokenService.issueTokens(user.id(), user.login()));
    }
}
