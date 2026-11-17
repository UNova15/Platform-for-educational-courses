package kira.user.adapter.in.web.controller.auth;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.web")
record CookieProperties(@NotNull Duration cookieTtl) {}
