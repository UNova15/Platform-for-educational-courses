package refactor.auth.application.ports.out.token;

import refactor.auth.domain.token.valueobject.RawRefreshToken;

public interface RefreshTokenGeneratePort {
    RawRefreshToken generateRefreshToken();
}
