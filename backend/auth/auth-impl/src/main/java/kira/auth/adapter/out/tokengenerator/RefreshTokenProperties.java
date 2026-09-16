package kira.auth.adapter.out.tokengenerator;

import kira.auth.domain.token.RefreshToken;
import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.tokens.refresh")
record RefreshTokenProperties(
        @Min(RefreshToken.MIN_REFRESH_TOKEN_LENGTH) int refreshTokenLength) {}
