package refactor.user.application.ports.out.token;

import java.util.Optional;
import refactor.user.domain.token.HashedToken;
import refactor.user.domain.token.RefreshToken;

public interface RefreshTokenRepositoryPort {
    RefreshToken save(RefreshToken token);

    void remove(RefreshToken token);

    Optional<RefreshToken> load(HashedToken token);
}
