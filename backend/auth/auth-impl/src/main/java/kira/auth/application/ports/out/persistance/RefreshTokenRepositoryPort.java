package kira.auth.application.ports.out.persistance;

import java.util.Optional;
import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;

public interface RefreshTokenRepositoryPort {
    RefreshToken save(RefreshToken token);

    void remove(RefreshToken token);

    Optional<RefreshToken> load(HashedRefreshToken token);
}
