package kira.user.application.ports.out.crypto;

import kira.user.domain.token.valueobject.HashedRefreshToken;
import kira.user.domain.token.valueobject.RawRefreshToken;

public interface TokenHashingPort {
    HashedRefreshToken hash(RawRefreshToken rawRefreshToken);
}
