package refactor.user.application.ports.in.usecase;

import refactor.user.application.ports.in.command.PairOfTokens;

public interface UpdateTokenUseCase {
    PairOfTokens updateTokens(String refreshToken);
}
