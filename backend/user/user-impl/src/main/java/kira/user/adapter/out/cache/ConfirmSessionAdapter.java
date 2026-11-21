package kira.user.adapter.out.cache;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionLoadPort;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionRemovePort;
import kira.user.application.ports.out.cache.confirmsession.ConfirmSessionSavePort;
import kira.user.domain.code.ConfirmationSession;
import kira.user.domain.user.valueobject.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import tools.jackson.databind.ObjectMapper;

@Repository
@RequiredArgsConstructor
public class ConfirmSessionAdapter implements ConfirmSessionLoadPort, ConfirmSessionSavePort, ConfirmSessionRemovePort {
    private static final String PREFIX = "confirm_session:";

    private final StringRedisTemplate redis;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<ConfirmationSession> load(Email email) {
        var key = key(email.value());
        String rawJson = redis.opsForValue().get(key);

        if (rawJson == null || rawJson.isBlank()) {
            return Optional.empty();
        }

        var entity = objectMapper.readValue(rawJson, SessionEntity.class);
        return Optional.of(SessionMapper.toDomain(entity));
    }

    @Override
    public void remove(Email email) {
        var key = key(email.value());
        redis.delete(key);
    }

    @Override
    public void save(ConfirmationSession session) {
        var entity = SessionMapper.toEntity(session);
        var key = key(entity.email());

        var ttl = Duration.between(Instant.now(), session.expireAt());
        if (!ttl.isPositive()) {
            return;
        }

        String rawJson = objectMapper.writeValueAsString(entity);

        redis.opsForValue().set(key, rawJson, ttl);
    }

    private String key(String email) {
        return PREFIX.concat(email);
    }
}
