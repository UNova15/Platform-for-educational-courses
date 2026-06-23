package org.platform.platformforeducationalcourses.dto.test;

import java.util.List;

public record TestQuestionCreateDto(
        String question,
        int orderIndex,
        List<TestQuestionAnswerCreateDto> options) {
}
