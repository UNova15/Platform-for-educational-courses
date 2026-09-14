package org.platform.platformforeducationalcourses.dto.lesson;

import java.time.LocalDateTime;
import refactor.course.implementation.domain.lesson.ContentType;

public record StudentLessonFindResponse(
        long id,
        long moduleId,
        String title,
        ContentType type,
        int orderIndex,
        boolean mandatory,
        LocalDateTime completedAt) {}
