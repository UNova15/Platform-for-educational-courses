package refactor.course.application.port.in.test.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.option.Option;
import refactor.course.domain.question.QuestionTitle;
import refactor.course.domain.test.TestDescription;

import java.util.List;

public record TestCreateCommand(
        @NotBlank String title,

        @Size(max = TestDescription.MAX_TEST_DESCRIPTION_LENGTH)
        String description,

        @PositiveOrZero int orderIndex,
        @NotNull @Valid List<@NotNull QuestionCommand> questions) {

    public record QuestionCommand(
            @NotBlank @Size(max = QuestionTitle.MAX_QUESTION_LENGTH)
            String question,

            @PositiveOrZero int orderIndex,
            @NotNull @Valid List<@NotNull OptionCommand> options) {}

    public record OptionCommand(
            @NotBlank @Size(max = Option.MAX_OPTION_LENGTH) String option, boolean isCorrect) {}
}
