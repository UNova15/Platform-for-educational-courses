package kira.user.application.ports.out.token;

import kira.user.domain.token.valueobject.RawRefreshToken;

public interface RefreshTokenGeneratePort {
    RawRefreshToken generateRefreshToken();
}
