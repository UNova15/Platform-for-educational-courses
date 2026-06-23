package org.platform.platformforeducationalcourses.dto.course.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CourseOption(
        @NotBlank @Size(max = 100) String option,
        boolean isCorrect) {
}
