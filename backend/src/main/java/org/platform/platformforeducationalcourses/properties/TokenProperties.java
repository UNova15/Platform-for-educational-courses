package org.platform.platformforeducationalcourses.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "app.token")
public record TokenProperties(@NotBlank String key, @NotNull Duration jwtTtl, @NotNull Duration refreshTtl) {
}
