package kira.infrastructure.accesstoken;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.tokens.access")
record AccessTokenProperties(
        @NotBlank String accessTokenGenerationKey,
        @NotNull Duration accessTokenTtl,
        int accessTokenClockSkew) {}
