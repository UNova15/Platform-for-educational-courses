package refactor.course.application.port.in.lesson.command.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.lesson.Content;
import refactor.course.domain.lesson.ContentType;
import refactor.course.domain.lesson.LessonTitle;

public record LessonUpdateCommand(
        @PositiveOrZero long lessonId,
        @PositiveOrZero long teacherId,

        @NotBlank @Size(max = LessonTitle.MAX_LESSON_TITLE_SIZE)
        String title,

        @PositiveOrZero int moduleId,
        @PositiveOrZero int orderIndex,
        @NotNull ContentType type,
        @NotBlank @Size(max = Content.MAX_CONTENT_LENGTH) String content,
        boolean mandatory) {}
