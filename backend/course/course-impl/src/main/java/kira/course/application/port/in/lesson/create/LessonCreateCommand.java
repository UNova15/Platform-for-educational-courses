package kira.course.application.port.in.lesson.create;

import kira.course.domain.common.Title;
import kira.course.domain.lesson.Content;
import kira.course.domain.lesson.ContentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record LessonCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,
        @NotNull ContentType type,
        @NotBlank @Size(max = Content.MAX_CONTENT_LENGTH) String content,
        @PositiveOrZero int orderIndex,
        boolean mandatory) {}
