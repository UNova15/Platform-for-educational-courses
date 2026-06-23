package org.platform.platformforeducationalcourses.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseUpdateRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500) String description,
        Tag tag) {
}
