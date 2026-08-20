package refactor.auth.adapter.out.token;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import refactor.auth.domain.token.RefreshToken;

@ConfigurationProperties(prefix = "app.tokens.refresh")
record RefreshTokenProperties(
        @Min(RefreshToken.MIN_REFRESH_TOKEN_LENGTH) int refreshTokenLength) {}
