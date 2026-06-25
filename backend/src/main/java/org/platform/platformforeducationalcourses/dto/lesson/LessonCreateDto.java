package org.platform.platformforeducationalcourses.dto.lesson;

import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record LessonCreateDto(
        long moduleId, String title, int orderIndex, ContentType type, String content, boolean mandatory) {}
