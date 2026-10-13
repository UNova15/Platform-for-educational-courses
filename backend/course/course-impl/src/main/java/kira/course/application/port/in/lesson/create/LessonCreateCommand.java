package kira.course.application.port.in.lesson.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import kira.course.domain.shared.Title;
import kira.course.domain.lesson.LessonContent;
import kira.course.domain.lesson.LessonContentType;

public record LessonCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,
        @NotNull LessonContentType type,
        @NotBlank @Size(max = LessonContent.MAX_CONTENT_LENGTH) String content,
        @PositiveOrZero int orderIndex,
        boolean mandatory) {}
