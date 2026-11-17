package kira.user.adapter.out.tokengenerator;

import java.security.SecureRandom;
import java.util.Base64;
import kira.user.application.ports.out.token.RefreshTokenGeneratePort;
import kira.user.domain.token.valueobject.RawRefreshToken;
import lombok.RequiredArgsConstructor;

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
