package auth.application.ports.out.token;

import refactor.auth.implemetnation.domain.token.valueobject.RawRefreshToken;

public interface RefreshTokenGeneratePort {
    RawRefreshToken generateRefreshToken();
}
