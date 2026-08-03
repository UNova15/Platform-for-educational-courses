package refactor.user.adapter.out.token.refresh;

import java.security.SecureRandom;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import refactor.user.application.ports.out.token.RefreshTokenGeneratePort;

@RequiredArgsConstructor
public class RefreshTokenGenerator implements RefreshTokenGeneratePort {
    private final SecureRandom secureRandom;
    private final Base64.Encoder encoder;
    private final int tokenLength;

    @Override
    public String generateRefreshToken() {
        byte[] bytes = new byte[tokenLength];
        secureRandom.nextBytes(bytes);
        return encoder.encodeToString(bytes);
    }
}
