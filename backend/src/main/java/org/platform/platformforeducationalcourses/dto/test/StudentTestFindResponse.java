package org.platform.platformforeducationalcourses.dto.test;

import java.time.LocalDateTime;

public record StudentTestFindResponse(
        long id,
        long moduleId,
        String description,
        int orderIndex,
        LocalDateTime startedAt,
        LocalDateTime completedAt,
        Integer score) {}
