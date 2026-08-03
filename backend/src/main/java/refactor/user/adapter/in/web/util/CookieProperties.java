package refactor.user.adapter.in.web.util;

import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.web")
public record CookieProperties(@NotNull Duration cookieTtl) {}
