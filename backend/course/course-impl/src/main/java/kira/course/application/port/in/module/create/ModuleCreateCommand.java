package kira.course.application.port.in.module.create;

import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record ModuleCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH)
        String title,

        @Size(max = Description.MAX_LENGTH)
        String description,

        @PositiveOrZero int orderIndex) {}
