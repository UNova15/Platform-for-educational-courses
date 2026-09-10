package refactor.progress.application.port.in.query.analytics;

import java.time.LocalDateTime;

public record EnrolledStudentsResult(long userId, LocalDateTime createdAt) {}
