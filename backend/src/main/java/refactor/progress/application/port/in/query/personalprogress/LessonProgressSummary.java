package refactor.progress.application.port.in.query.personalprogress;

import java.time.LocalDateTime;

public record LessonProgressSummary(long lessonId, LocalDateTime completedAt) {}
