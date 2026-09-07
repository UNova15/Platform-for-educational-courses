package refactor.course.application.port.in.query.learning;

import refactor.course.domain.course.Tag;

import java.time.LocalDateTime;

public record EnrolledCourse(long id, long teacherId, Tag tag, LocalDateTime createdAt, String title) {}
