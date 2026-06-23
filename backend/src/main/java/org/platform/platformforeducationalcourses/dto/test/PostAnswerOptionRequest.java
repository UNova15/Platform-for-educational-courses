package org.platform.platformforeducationalcourses.dto.test;

import jakarta.validation.constraints.PositiveOrZero;

public record PostAnswerOptionRequest(
        @PositiveOrZero long questionId,
        @PositiveOrZero long optionId
) {
}
