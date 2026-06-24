package org.platform.platformforeducationalcourses.dto.enrollment;

import java.time.LocalDateTime;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseEnrolledFindResponse(
        long id, long teacherId, String title, String description, Tag tag, LocalDateTime createdAt) {}
