package refactor.course.application.port.in.query.catalog;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import refactor.course.domain.course.Tag;

public record CursorCourseQuery(
        @PositiveOrZero Long cursor,
        @PositiveOrZero @Max(100) int limit,
        Tag tag) {}
