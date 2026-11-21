package kira.user.adapter.out.cache;

import kira.user.domain.code.valueobject.DelaySteps;

import java.time.Instant;

public record SessionEntity(
        String username,
        String email,
        String code,
        String hashedPassword,
        int numberOfInputAttempts,
        DelaySteps step,
        Instant lastLetterSent,
        Instant expireAt) {}
