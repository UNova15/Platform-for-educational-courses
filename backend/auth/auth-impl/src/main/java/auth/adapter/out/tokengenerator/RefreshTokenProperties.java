package auth.adapter.out.tokengenerator;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;
import refactor.auth.implemetnation.domain.token.RefreshToken;

@ConfigurationProperties(prefix = "app.tokens.refresh")
record RefreshTokenProperties(
        @Min(RefreshToken.MIN_REFRESH_TOKEN_LENGTH) int refreshTokenLength) {}
