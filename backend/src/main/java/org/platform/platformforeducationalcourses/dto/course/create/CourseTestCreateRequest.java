package org.platform.platformforeducationalcourses.dto.course.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CourseTestCreateRequest(
        @PositiveOrZero int orderIndex,
        String description,
        @NotNull @Size(max = 100) List<CourseTestQuestion> questions) {
}
