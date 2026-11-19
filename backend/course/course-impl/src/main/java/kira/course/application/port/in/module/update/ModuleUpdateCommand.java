package kira.course.application.port.in.module.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;

public record ModuleUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,

        @Size(max = Description.MAX_LENGTH) String description,

        @PositiveOrZero int orderIndex) {}
