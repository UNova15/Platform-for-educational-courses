package org.platform.platformforeducationalcourses.dto.course.createdto;

import java.time.Instant;
import java.util.List;
import org.platform.platformforeducationalcourses.domain.course.Tag;

public record CourseCreateDto(
        String title,
        String description,
        Tag tag,
        List<CourseModuleCreateDto> modules,
        Instant createdAt,
        long teacherId) {}
