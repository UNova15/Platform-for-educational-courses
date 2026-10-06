package kira.course.application.port.in.query.catalog;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import kira.course.domain.course.Tag;

// TODO сделать обработку null
public record CursorCourseQuery(
        @PositiveOrZero Long cursor,
        @PositiveOrZero @Max(100) int limit,
        Tag tag) {}
