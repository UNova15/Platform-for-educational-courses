package course.application.port.in.module.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.implementation.domain.common.Description;
import refactor.course.implementation.domain.common.Title;

public record ModuleUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH)
        String title,

        @Size(max = Description.MAX_LENGTH)
        String description,

        @PositiveOrZero int orderIndex) {}
