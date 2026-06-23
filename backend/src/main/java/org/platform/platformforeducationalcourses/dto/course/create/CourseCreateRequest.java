package org.platform.platformforeducationalcourses.dto.course.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.platform.platformforeducationalcourses.domain.course.Tag;

import java.util.List;

public record CourseCreateRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500)String description,
        Tag tag,
        @Size(max = 100) List<CourseModuleCreateRequest> modules) {
}
