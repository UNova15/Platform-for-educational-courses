package org.platform.platformforeducationalcourses.dto.test.studentattemptresponse;

import java.util.List;

public record TestQuestion(
        long questionId, int orderIndex, String question, List<QuestionOption> options, long selectedId) {}
