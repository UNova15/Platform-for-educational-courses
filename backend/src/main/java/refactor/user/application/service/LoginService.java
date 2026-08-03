package refactor.user.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import refactor.user.application.ports.in.command.LoginCommand;
import refactor.user.application.ports.in.command.PairOfTokens;
import refactor.user.application.ports.in.usecase.LoginUseCase;
import refactor.user.application.ports.out.auth.LoginPort;
import refactor.user.domain.user.User;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUseCase {
    private final LoginPort securityPort;
    private final TokenService tokenService;

    @Override
    public PairOfTokens login(LoginCommand request) {
        User user = securityPort.login(request.login(), request.password());

        return tokenService.createTokens(user.getId(), user.getLogin(), user.getRole());
    }
}
