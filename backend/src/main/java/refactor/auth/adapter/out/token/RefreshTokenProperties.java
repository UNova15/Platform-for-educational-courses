package refactor.auth.adapter.out.token;

import jakarta.validation.constraints.Min;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.tokens.refresh")
record RefreshTokenProperties(@Min(32) int refreshTokenLength) {}
