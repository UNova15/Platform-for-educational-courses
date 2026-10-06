package kira.course.application.port.in.lesson.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import kira.course.domain.common.Title;
import kira.course.domain.lesson.Content;
import kira.course.domain.lesson.ContentType;

public record LessonUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,

        @PositiveOrZero int orderIndex,
        @NotNull ContentType type,
        @NotBlank @Size(max = Content.MAX_CONTENT_LENGTH) String content,
        boolean mandatory) {}
