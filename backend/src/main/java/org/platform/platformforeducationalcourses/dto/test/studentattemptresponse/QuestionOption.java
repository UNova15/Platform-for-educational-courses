package org.platform.platformforeducationalcourses.dto.test.studentattemptresponse;

public record QuestionOption(
        long id,
        long questionId,
        String option,
        boolean isCorrect
) {
}
