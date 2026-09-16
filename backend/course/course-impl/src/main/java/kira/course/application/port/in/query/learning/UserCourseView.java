package kira.course.application.port.in.query.learning;

import kira.course.domain.course.Tag;

import java.time.LocalDateTime;

public record UserCourseView(long id, long teacherId, Tag tag, LocalDateTime createdAt, String title) {}
