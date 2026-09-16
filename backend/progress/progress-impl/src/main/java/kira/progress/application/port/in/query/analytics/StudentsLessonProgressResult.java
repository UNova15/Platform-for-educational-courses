package kira.progress.application.port.in.query.analytics;

import java.time.LocalDateTime;

public record StudentsLessonProgressResult(long userId, LocalDateTime completedAt) {}
