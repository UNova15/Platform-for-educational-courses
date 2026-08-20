package refactor.course.application.port.in.test.command.remove;

import jakarta.validation.constraints.PositiveOrZero;

public record RemoveTestCommand(
        @PositiveOrZero long requesterId, @PositiveOrZero long testId) {}
