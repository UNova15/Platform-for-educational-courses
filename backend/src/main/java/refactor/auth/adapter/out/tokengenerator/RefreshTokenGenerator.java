package refactor.auth.adapter.out.tokengenerator;

import java.security.SecureRandom;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import refactor.auth.application.ports.out.token.RefreshTokenGeneratePort;
import refactor.auth.domain.token.valueobject.RawRefreshToken;

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
