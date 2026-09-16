package kira.auth.application.ports.out.crypto;


import kira.auth.domain.token.valueobject.HashedRefreshToken;
import kira.auth.domain.token.valueobject.RawRefreshToken;

public interface TokenHashingPort {
    HashedRefreshToken hash(RawRefreshToken rawRefreshToken);
}
