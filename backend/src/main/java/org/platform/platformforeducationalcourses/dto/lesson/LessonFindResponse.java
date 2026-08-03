package org.platform.platformforeducationalcourses.dto.lesson;

import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record LessonFindResponse(
        long id, long moduleId, String title, ContentType type, String content, int orderIndex, boolean mandatory) {}
