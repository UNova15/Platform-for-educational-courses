package kira.auth.application.ports.out.persistance.token;

import kira.auth.domain.token.RefreshToken;

public interface RefreshTokenRemovePort {
    void remove(RefreshToken token);
}
