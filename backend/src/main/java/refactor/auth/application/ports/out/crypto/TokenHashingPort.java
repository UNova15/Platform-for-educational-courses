package refactor.auth.application.ports.out.crypto;

import refactor.auth.domain.token.valueobject.HashedRefreshToken;
import refactor.auth.domain.token.valueobject.RawRefreshToken;

public interface TokenHashingPort {
    HashedRefreshToken hash(RawRefreshToken rawRefreshToken);
}
