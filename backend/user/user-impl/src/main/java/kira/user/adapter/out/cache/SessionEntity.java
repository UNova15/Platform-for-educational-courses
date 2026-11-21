package kira.user.adapter.out.cache;

import java.time.Instant;
import kira.user.domain.code.valueobject.DelaySteps;

public record SessionEntity(
        String username,
        String email,
        String code,
        String hashedPassword,
        int numberOfInputAttempts,
        DelaySteps step,
        Instant lastLetterSent,
        Instant expireAt) {}
