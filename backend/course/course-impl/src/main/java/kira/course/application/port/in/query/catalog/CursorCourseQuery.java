package kira.course.application.port.in.query.catalog;

import kira.course.domain.course.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;

public record CursorCourseQuery(
        @PositiveOrZero Long cursor,
        @PositiveOrZero @Max(100) int limit,
        Tag tag) {}
