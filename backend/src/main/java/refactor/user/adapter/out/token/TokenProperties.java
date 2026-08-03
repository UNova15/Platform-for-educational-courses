package refactor.user.adapter.out.token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.tokens")
public record TokenProperties(
        @NotBlank String accessTokenGenerationKey,
        @NotNull Duration accessTokenTtl,
        @NotNull Integer accessTokenClockSkew,
        @NotNull Integer refreshTokenLength,
        @NotNull Duration refreshTokenTtl) {}
