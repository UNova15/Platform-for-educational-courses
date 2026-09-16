package kira.course.application.port.in.course.update;

import kira.course.domain.common.Description;
import kira.course.domain.common.Title;
import kira.course.domain.course.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CourseUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH)
        String title,

        @Size(max = Description.MAX_LENGTH)
        String description,

        Tag tag) {}
