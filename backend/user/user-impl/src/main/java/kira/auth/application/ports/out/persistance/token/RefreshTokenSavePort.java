package kira.auth.application.ports.out.persistance.token;

import kira.auth.domain.token.RefreshToken;

public interface RefreshTokenSavePort {
    RefreshToken save(RefreshToken token);
}
