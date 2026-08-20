package refactor.course.application.port.in.test.command.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.application.port.in.test.command.create.QuestionUpdateCommand;
import refactor.course.domain.test.Test;
import refactor.course.domain.test.TestDescription;

import java.util.List;

public record TestUpdateCommand(
        @PositiveOrZero long testId,
        @PositiveOrZero long teacherId,
        @NotBlank String title,

        @Size(max = TestDescription.MAX_TEST_DESCRIPTION_LENGTH)
        String description,

        @PositiveOrZero int orderIndex,
        @NotNull @Size(max = Test.MAX_QUESTION_COUNT) List<@NotNull QuestionUpdateCommand> questions) {}
