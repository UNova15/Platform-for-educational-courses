package kira.course.application.port.in.course.create;

import java.time.LocalDateTime;

public record CourseCreateResponse(long courseId, String title, LocalDateTime createdAt) {}
