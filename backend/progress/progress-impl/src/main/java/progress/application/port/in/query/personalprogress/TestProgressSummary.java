package progress.application.port.in.query.personalprogress;

import java.time.LocalDateTime;

public record TestProgressSummary(long testId, LocalDateTime startedAt, LocalDateTime completedAt, int score) {}
