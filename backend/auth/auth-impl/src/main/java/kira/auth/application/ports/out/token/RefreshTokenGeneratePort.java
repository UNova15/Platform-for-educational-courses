package kira.auth.application.ports.out.token;


import kira.auth.domain.token.valueobject.RawRefreshToken;

public interface RefreshTokenGeneratePort {
    RawRefreshToken generateRefreshToken();
}
