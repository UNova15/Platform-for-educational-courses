package course.application.port.in.query.learning;

import refactor.course.implementation.domain.course.Tag;

import java.time.LocalDateTime;

public record UserCourseView(long id, long teacherId, Tag tag, LocalDateTime createdAt, String title) {}
