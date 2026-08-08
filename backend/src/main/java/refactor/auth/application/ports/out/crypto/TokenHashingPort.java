package refactor.auth.application.ports.out.crypto;

import refactor.auth.domain.token.HashedToken;
import refactor.auth.domain.token.RawToken;

public interface TokenHashingPort {
    HashedToken hash(RawToken rawToken);
}
