package org.platform.platformforeducationalcourses.dto.lesson;

import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record LessonUpdateDto(long moduleId, long lessonId, int orderIndex, String title, ContentType type,
                              String content, boolean mandatory, long newModuleId) {
}
