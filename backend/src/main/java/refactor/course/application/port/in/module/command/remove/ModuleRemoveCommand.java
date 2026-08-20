package refactor.course.application.port.in.module.command.remove;

import jakarta.validation.constraints.PositiveOrZero;

public record ModuleRemoveCommand(
        @PositiveOrZero long moduleId,
        @PositiveOrZero long teacherId
) {}
