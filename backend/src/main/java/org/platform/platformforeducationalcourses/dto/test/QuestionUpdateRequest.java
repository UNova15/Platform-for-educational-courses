package org.platform.platformforeducationalcourses.dto.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record QuestionUpdateRequest(
        @NotBlank String question,
        @PositiveOrZero int orderIndex,
        @NotNull List<AnswerOptionUpdateRequest> options
) {
}
