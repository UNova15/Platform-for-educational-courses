package org.platform.platformforeducationalcourses.dto.test;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;

public record TestUpdateRequest(
        String description,
        @PositiveOrZero int orderIndex,
        @NotNull List<QuestionUpdateRequest> questions) {}
