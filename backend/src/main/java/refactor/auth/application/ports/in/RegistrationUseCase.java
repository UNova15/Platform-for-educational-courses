package refactor.auth.application.ports.in;

public interface RegistrationUseCase {
    PairOfTokens registration(RegistrationCommand request);
}
