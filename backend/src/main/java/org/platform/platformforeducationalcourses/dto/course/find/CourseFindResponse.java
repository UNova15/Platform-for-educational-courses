package org.platform.platformforeducationalcourses.dto.course.find;

import java.time.LocalDateTime;
import java.util.List;
import refactor.course.implementation.domain.course.Tag;

public record CourseFindResponse(
        long id,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt,
        List<CourseModuleFindResponse> modules) {}
