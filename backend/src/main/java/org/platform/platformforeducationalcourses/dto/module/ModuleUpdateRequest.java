package org.platform.platformforeducationalcourses.dto.module;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ModuleUpdateRequest(
        @NotBlank @Size(max = 100) String title,
        @Size(max = 500) String description,
        @PositiveOrZero int order_index) {}
