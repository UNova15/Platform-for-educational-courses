package refactor.course.application.port.in.module.command.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.module.ModuleDescription;
import refactor.course.domain.module.ModuleTitle;

public record ModuleUpdateCommand(
        @PositiveOrZero long moduleId,
        @PositiveOrZero long teacherId,

        @NotBlank @Size(max = ModuleTitle.MAX_MODULE_TITLE_SIZE)
        String title,

        @Size(max = ModuleDescription.MAX_MODULE_DESCRIPTION_SIZE)
        String description,

        @PositiveOrZero int orderIndex) {}
