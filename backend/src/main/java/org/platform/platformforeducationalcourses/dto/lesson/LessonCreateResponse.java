package org.platform.platformforeducationalcourses.dto.lesson;

public record LessonCreateResponse(long id, long moduleId, String title, int orderIndex) {
}
