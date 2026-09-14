package auth.application.ports.out.crypto;

import refactor.auth.implemetnation.domain.token.valueobject.HashedRefreshToken;
import refactor.auth.implemetnation.domain.token.valueobject.RawRefreshToken;

public interface TokenHashingPort {
    HashedRefreshToken hash(RawRefreshToken rawRefreshToken);
}
