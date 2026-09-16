package kira.auth.adapter.out.tokengenerator;

import java.security.SecureRandom;
import java.util.Base64;

import kira.auth.domain.token.valueobject.RawRefreshToken;
import lombok.RequiredArgsConstructor;
import kira.auth.application.ports.out.token.RefreshTokenGeneratePort;

@RequiredArgsConstructor
class RefreshTokenGenerator implements RefreshTokenGeneratePort {
    private final SecureRandom secureRandom;
    private final Base64.Encoder encoder;
    private final int tokenLength;

    @Override
    public RawRefreshToken generateRefreshToken() {
        byte[] bytes = new byte[tokenLength];
        secureRandom.nextBytes(bytes);
        String encoded = encoder.encodeToString(bytes);

        return RawRefreshToken.of(encoded);
    }
}
