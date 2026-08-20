package refactor.course.application.port.in.lesson.command.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.lesson.Content;
import refactor.course.domain.lesson.ContentType;

import static refactor.course.domain.lesson.LessonTitle.MAX_LESSON_TITLE_SIZE;

public record LessonCreateCommand(
        @PositiveOrZero long teacherId,
        @PositiveOrZero long moduleId,
        @NotBlank @Size(max = MAX_LESSON_TITLE_SIZE) String title,
        @NotNull ContentType type,
        @NotBlank @Size(max = Content.MAX_CONTENT_LENGTH) String content,
        @PositiveOrZero int orderIndex,
        boolean mandatory) {}
