package refactor.auth.application.ports.in;

public interface LoginUseCase {
    PairOfTokens login(LoginCommand request);
}
