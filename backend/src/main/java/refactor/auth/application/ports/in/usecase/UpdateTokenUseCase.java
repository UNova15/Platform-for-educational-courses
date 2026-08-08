package refactor.auth.application.ports.in.usecase;

public interface UpdateTokenUseCase {
    PairOfTokens updateTokens(String refreshToken);
}
