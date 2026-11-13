package kira.course.application.port.in.course.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kira.course.domain.course.Tag;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;

public record CourseUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,

        @Size(max = Description.MAX_LENGTH) String description,

        Tag tag) {}
