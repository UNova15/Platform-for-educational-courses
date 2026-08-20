package refactor.course.application.port.in.course.command.remove;

import jakarta.validation.constraints.PositiveOrZero;

public record CourseRemoveCommand(
        @PositiveOrZero long teacherId, @PositiveOrZero long courseId) {}
