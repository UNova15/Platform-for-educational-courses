package refactor.course.application.port.in.course.command.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import refactor.course.domain.internal.common.Description;
import refactor.course.domain.internal.common.Title;
import refactor.course.domain.internal.course.Tag;

public record CourseUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH)
        String title,

        @Size(max = Description.MAX_LENGTH)
        String description,

        Tag tag) {}
