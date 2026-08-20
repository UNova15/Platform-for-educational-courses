package refactor.auth.application.ports.in;

public interface UpdateTokenUseCase {
    PairOfTokens updateTokens(String refreshToken);
}
