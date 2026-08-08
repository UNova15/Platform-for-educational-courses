package refactor.auth.application.ports.in.usecase;

public interface LoginUseCase {
    PairOfTokens login(LoginCommand request);
}
