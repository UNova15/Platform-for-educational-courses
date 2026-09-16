package kira.course.application.port.in.query.catalog;

import kira.course.domain.course.Tag;

import java.time.LocalDateTime;

public record CourseCursorView(long id, long teacherId, String title, String description, Tag tag, LocalDateTime createdAt) {}
