package org.platform.platformforeducationalcourses.dto.test.studentattemptresponse;

import java.util.List;

public record TestReview(
        long testId,
        String description,
        int orderIndex,
        List<TestQuestion> questions
) {
}
