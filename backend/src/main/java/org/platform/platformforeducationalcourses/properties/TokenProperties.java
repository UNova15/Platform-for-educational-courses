package org.platform.platformforeducationalcourses.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.token")
public record TokenProperties(
        @NotBlank String key,
        @NotNull Duration jwtTtl,
        @NotNull Duration refreshTtl) {}
