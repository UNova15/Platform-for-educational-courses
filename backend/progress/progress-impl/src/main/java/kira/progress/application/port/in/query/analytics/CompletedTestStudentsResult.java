package kira.progress.application.port.in.query.analytics;

import java.time.LocalDateTime;

public record CompletedTestStudentsResult(
        long submissionId, long studentId, LocalDateTime startedAt, LocalDateTime completedAt, int score) {}
