package org.platform.platformforeducationalcourses.dto.course.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseCreateRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500) String description,
        Tag tag,
        @Size(max = 100) List<CourseModuleCreateRequest> modules) {}
