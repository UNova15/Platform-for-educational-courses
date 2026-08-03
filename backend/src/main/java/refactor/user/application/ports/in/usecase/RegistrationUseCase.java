package refactor.user.application.ports.in.usecase;

import refactor.user.application.ports.in.command.PairOfTokens;
import refactor.user.application.ports.in.command.RegistrationCommand;

public interface RegistrationUseCase {
    PairOfTokens registration(RegistrationCommand request);
}
