package refactor.course.application.port.in.lesson.command.remove;

import jakarta.validation.constraints.PositiveOrZero;

public record LessonRemoveCommand(
        @PositiveOrZero long teacherId,
        @PositiveOrZero long lessonId) {}
