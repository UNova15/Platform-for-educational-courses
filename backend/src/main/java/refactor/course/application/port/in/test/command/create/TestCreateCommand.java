package refactor.course.application.port.in.test.command.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.test.TestDescription;

import java.util.List;

public record TestCreateCommand(
        @NotBlank String title,
        @PositiveOrZero long teacherId,
        @PositiveOrZero long moduleId,

        @Size(max = TestDescription.MAX_TEST_DESCRIPTION_LENGTH)
        String description,

        @PositiveOrZero int orderIndex,
        @NotNull List<@NotNull QuestionCreateCommand> questions) {}
