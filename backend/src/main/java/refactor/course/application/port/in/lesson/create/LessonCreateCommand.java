package refactor.course.application.port.in.lesson.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.common.Title;
import refactor.course.domain.lesson.Content;
import refactor.course.domain.lesson.ContentType;

public record LessonCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,
        @NotNull ContentType type,
        @NotBlank @Size(max = Content.MAX_CONTENT_LENGTH) String content,
        @PositiveOrZero int orderIndex,
        boolean mandatory) {}
