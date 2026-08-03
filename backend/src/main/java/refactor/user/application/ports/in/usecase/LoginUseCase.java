package refactor.user.application.ports.in.usecase;

import refactor.user.application.ports.in.command.LoginCommand;
import refactor.user.application.ports.in.command.PairOfTokens;

public interface LoginUseCase {
    PairOfTokens login(LoginCommand request);
}
