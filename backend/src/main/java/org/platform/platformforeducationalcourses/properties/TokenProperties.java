package org.platform.platformforeducationalcourses.properties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

// TODO вынести настройки пароля/логина
@ConfigurationProperties(prefix = "app.security")
public record TokenProperties(
        @NotBlank String key,
        @NotNull Duration jwtTtl,
        @NotNull Duration refreshTtl,
        @NotBlank String minPasswordLength) {}
