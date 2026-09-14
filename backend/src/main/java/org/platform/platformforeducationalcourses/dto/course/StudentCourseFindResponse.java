package org.platform.platformforeducationalcourses.dto.course;

import java.time.LocalDateTime;
import java.util.List;
import refactor.course.implementation.domain.course.Tag;
import org.platform.platformforeducationalcourses.dto.module.StudentModuleFindResponse;

public record StudentCourseFindResponse(
        Long id,
        Long teacherId,
        String title,
        String description,
        Tag tag,
        LocalDateTime createdAt,
        List<StudentModuleFindResponse> modules) {}
