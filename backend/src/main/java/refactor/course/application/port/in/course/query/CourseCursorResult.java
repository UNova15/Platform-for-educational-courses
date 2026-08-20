package refactor.course.application.port.in.course.query;

import java.time.LocalDateTime;
import refactor.course.domain.course.Tag;

public record CourseCursorResult(long id, long teacherId, String title, String description, Tag tag, LocalDateTime createdAt) {}
