package kira.course.application.port.in.query.learning;

import java.time.LocalDateTime;
import kira.course.domain.course.Tag;

public record UserCourseView(long id, long teacherId, Tag tag, LocalDateTime createdAt, String title) {}
