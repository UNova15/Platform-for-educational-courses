package org.platform.platformforeducationalcourses.dto.test;

import java.util.List;

public record AnswerPostDto(long questionId, List<Long> optionIds) {}
