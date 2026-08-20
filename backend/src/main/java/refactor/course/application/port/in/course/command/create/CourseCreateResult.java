package refactor.course.application.port.in.course.command.create;

import java.time.LocalDateTime;

public record CourseCreateResult(long courseId, String title, LocalDateTime createdAt) {}
