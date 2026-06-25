package org.platform.platformforeducationalcourses.dto.test;

import java.util.List;

public record TestCreateDto(int orderIndex, String description, List<TestQuestionCreateDto> questions) {}
