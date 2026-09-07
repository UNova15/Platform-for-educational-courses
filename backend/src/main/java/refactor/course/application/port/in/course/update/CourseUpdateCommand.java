package refactor.course.application.port.in.course.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import refactor.course.domain.common.Description;
import refactor.course.domain.common.Title;
import refactor.course.domain.course.Tag;

public record CourseUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH)
        String title,

        @Size(max = Description.MAX_LENGTH)
        String description,

        Tag tag) {}
