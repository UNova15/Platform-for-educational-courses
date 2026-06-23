package org.platform.platformforeducationalcourses.dto.test;

import java.util.List;

public record TestUpdateDto(
        long moduleId,
        long testId,
        String description,
        int orderIndex,
        List<QuestionUpdateDto> questions
        ) {
}
