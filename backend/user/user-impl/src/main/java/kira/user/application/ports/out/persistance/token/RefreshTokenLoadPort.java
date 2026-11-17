package kira.user.application.ports.out.persistance.token;

import java.util.Optional;
import kira.user.domain.token.RefreshToken;
import kira.user.domain.token.valueobject.HashedRefreshToken;

public interface RefreshTokenLoadPort {
    Optional<RefreshToken> load(HashedRefreshToken token);
}
