package kira.course.application.port.in.course.create;

import java.time.LocalDateTime;

public record CourseCreateResult(long courseId, String title, LocalDateTime createdAt) {}
