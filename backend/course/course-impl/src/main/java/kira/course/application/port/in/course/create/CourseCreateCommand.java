package kira.course.application.port.in.course.create;

import jakarta.validation.constraints.*;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.course.Tag;

public record CourseCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,
        @Size(max = Description.MAX_LENGTH) String description,
        Tag tag){}
