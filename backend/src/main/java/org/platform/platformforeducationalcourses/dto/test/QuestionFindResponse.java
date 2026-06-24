package org.platform.platformforeducationalcourses.dto.test;

import java.util.List;

public record QuestionFindResponse(
        long questionId, int orderIndex, String question, List<AnswerFindResponse> answers) {}
