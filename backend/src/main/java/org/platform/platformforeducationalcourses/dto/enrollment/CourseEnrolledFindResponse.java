package org.platform.platformforeducationalcourses.dto.enrollment;

import org.platform.platformforeducationalcourses.domain.course.Tag;

import java.time.LocalDateTime;

public record CourseEnrolledFindResponse(
        long id,
        long teacherId,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt
) {
}
