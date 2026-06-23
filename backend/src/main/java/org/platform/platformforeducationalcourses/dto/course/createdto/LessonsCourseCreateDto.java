package org.platform.platformforeducationalcourses.dto.course.createdto;

import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record LessonsCourseCreateDto(
        String title,
        ContentType type,
        String content,
        int orderIndex,
        boolean mandatory
) {
}
