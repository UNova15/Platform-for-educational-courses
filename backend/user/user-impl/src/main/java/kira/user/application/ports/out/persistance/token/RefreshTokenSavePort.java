package kira.user.application.ports.out.persistance.token;

import kira.user.domain.token.RefreshToken;

public interface RefreshTokenSavePort {
    RefreshToken save(RefreshToken token);
}
