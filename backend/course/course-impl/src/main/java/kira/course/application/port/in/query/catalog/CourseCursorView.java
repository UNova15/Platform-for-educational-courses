package kira.course.application.port.in.query.catalog;

import java.time.LocalDateTime;
import kira.course.domain.course.Tag;

public record CourseCursorView(
        long id, long teacherId, String title, String description, Tag tag, LocalDateTime createdAt) {}
