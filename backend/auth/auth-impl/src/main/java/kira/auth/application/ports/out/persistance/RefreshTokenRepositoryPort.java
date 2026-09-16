package kira.auth.application.ports.out.persistance;

import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;

import java.util.Optional;

public interface RefreshTokenRepositoryPort {
    RefreshToken save(RefreshToken token);

    void remove(RefreshToken token);

    Optional<RefreshToken> load(HashedRefreshToken token);
}
