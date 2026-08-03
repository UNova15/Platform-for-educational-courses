package refactor.user.application.ports.out.token;

import refactor.user.domain.token.HashedToken;
import refactor.user.domain.token.RawToken;

public interface TokenHashingPort {
    HashedToken hash(RawToken rawToken);
}
