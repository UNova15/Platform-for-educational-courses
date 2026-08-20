package refactor.course.application.port.in.course.command.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.course.CourseDescription;
import refactor.course.domain.course.CourseTitle;
import refactor.course.domain.course.Tag;

public record CourseUpdateCommand(
        @PositiveOrZero long teacherId,
        @PositiveOrZero long courseId,

        @NotBlank @Size(max = CourseTitle.MAX_COURSE_TITLE_SIZE)
        String title,

        @Size(max = CourseDescription.MAX_COURSE_DESCRIPTION_SIZE)
        String description,

        Tag tag) {}
