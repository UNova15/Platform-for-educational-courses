package org.platform.platformforeducationalcourses.dto.test;

import java.util.List;

public record TestFindResponse(
        long testId,
        String description,
        int orderIndex,
        List<QuestionFindResponse> questions
) {
}
