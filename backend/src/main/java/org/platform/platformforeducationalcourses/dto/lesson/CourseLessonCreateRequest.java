package org.platform.platformforeducationalcourses.dto.lesson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record CourseLessonCreateRequest(
        @NotBlank @Size(max = 100) String title,
        @NotNull ContentType type,
        @NotBlank String content,
        @PositiveOrZero int orderIndex,
        boolean mandatory) {
}
