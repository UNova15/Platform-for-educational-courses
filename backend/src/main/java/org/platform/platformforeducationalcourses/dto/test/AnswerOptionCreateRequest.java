package org.platform.platformforeducationalcourses.dto.test;

import jakarta.validation.constraints.NotBlank;

public record AnswerOptionCreateRequest(
        @NotBlank String option,
        boolean isCorrect
) {
}
