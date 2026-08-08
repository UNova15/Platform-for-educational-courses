package refactor.auth.application.ports.in.usecase;

public interface RegistrationUseCase {
    PairOfTokens registration(RegistrationCommand request);
}
