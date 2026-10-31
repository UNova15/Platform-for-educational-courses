package kira.progress.application.port.in.query.analytics;

import java.time.LocalDateTime;

public record CompletedTestStudentsResult(
        long id, long userId, LocalDateTime startedAt, LocalDateTime completedAt, int score) {}
