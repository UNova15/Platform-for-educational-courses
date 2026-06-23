package org.platform.platformforeducationalcourses.dto.test;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record TestCreateRequest(
        String description,
        @PositiveOrZero int orderIndex,
        @NotNull List<QuestionCreateRequest> questions
) {
}
