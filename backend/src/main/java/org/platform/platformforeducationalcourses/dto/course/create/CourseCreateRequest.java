package org.platform.platformforeducationalcourses.dto.course.create;

import static org.platform.platformforeducationalcourses.properties.constatnts.CourseValidationConstants.*;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseCreateRequest(
        @NotBlank @Size(max = MAX_COURSE_TITLE_SIZE) String title,
        @Size(max = MAX_COURSE_DESCRIPTION_SIZE) String description,
        Tag tag,
        @Size(max = MAX_MODULES_COUNT) List<ModuleCreateRequest> modules) {}
