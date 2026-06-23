package org.platform.platformforeducationalcourses.dto.course;

import org.platform.platformforeducationalcourses.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;

import java.time.LocalDateTime;
import java.util.List;

public record StudentCourseFindResponse(
        Long id,
        Long teacherId,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt,
        List<StudentModuleFindResponse> modules
) {
}
