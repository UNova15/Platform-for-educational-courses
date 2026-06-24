package org.platform.platformforeducationalcourses.dto.test;

import java.util.List;

public record QuestionUpdateDto(String question, int orderIndex, List<AnswerOptionUpdateDto> options) {}
