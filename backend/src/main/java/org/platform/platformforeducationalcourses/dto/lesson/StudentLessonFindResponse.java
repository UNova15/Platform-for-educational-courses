package org.platform.platformforeducationalcourses.dto.lesson;

import org.platform.platformforeducationalcourses.domain.course.ContentType;

import java.time.LocalDateTime;

public record StudentLessonFindResponse(
        long id,
        long moduleId,
        String title,
        ContentType type,
        int orderIndex,
        boolean mandatory,
        LocalDateTime completedAt
){
}
