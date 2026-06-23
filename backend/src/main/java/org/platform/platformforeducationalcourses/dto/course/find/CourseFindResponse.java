package org.platform.platformforeducationalcourses.dto.course.find;

import org.platform.platformforeducationalcourses.domain.course.Tag;

import java.time.LocalDateTime;
import java.util.List;

public record CourseFindResponse(
        long id,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt,
        List<CourseModuleFindResponse> modules
) {
}
