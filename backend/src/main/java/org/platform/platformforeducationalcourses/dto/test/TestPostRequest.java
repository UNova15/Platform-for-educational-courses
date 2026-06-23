package org.platform.platformforeducationalcourses.dto.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

public record TestPostRequest(
        @NotBlank List<PostAnswerOptionRequest> answers
) {
}
