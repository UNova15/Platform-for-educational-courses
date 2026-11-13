package kira.auth.application.ports.out.persistance.token;

import java.util.Optional;
import kira.auth.domain.token.RefreshToken;
import kira.auth.domain.token.valueobject.HashedRefreshToken;

public interface RefreshTokenLoadPort {
    Optional<RefreshToken> load(HashedRefreshToken token);
}
