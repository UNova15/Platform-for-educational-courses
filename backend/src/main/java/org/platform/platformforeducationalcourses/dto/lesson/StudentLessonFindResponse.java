package org.platform.platformforeducationalcourses.dto.lesson;

import java.time.LocalDateTime;
import org.platform.platformforeducationalcourses.domain.course.ContentType;

public record StudentLessonFindResponse(
        long id,
        long moduleId,
        String title,
        ContentType type,
        int orderIndex,
        boolean mandatory,
        LocalDateTime completedAt) {}
