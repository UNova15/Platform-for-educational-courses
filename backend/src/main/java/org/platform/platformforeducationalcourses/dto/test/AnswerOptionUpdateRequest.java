package org.platform.platformforeducationalcourses.dto.test;

import jakarta.validation.constraints.NotBlank;

public record AnswerOptionUpdateRequest(@NotBlank String option, boolean isCorrect) {}
