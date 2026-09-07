package refactor.auth.application.ports.out.persistance;

import java.util.Optional;
import refactor.auth.domain.token.valueobject.HashedRefreshToken;
import refactor.auth.domain.token.RefreshToken;

public interface RefreshTokenRepositoryPort {
    RefreshToken save(RefreshToken token);

    void remove(RefreshToken token);

    Optional<RefreshToken> load(HashedRefreshToken token);
}
