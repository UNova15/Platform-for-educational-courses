package kira.user.application.ports.out.persistance.token;

import kira.user.domain.token.RefreshToken;

public interface RefreshTokenRemovePort {
    void remove(RefreshToken token);
}
