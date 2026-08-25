package refactor.course.application.port.in.lesson.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.internal.common.Title;
import refactor.course.domain.internal.lesson.Content;
import refactor.course.domain.internal.lesson.ContentType;

public record LessonUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH)
        String title,

        @PositiveOrZero int orderIndex,
        @NotNull ContentType type,
        @NotBlank @Size(max = Content.MAX_CONTENT_LENGTH) String content,
        boolean mandatory) {}
