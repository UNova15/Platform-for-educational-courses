package course.application.port.in.test.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.implementation.domain.common.Description;
import refactor.course.implementation.domain.common.Title;
import refactor.course.implementation.domain.test.Question;
import refactor.course.implementation.domain.test.valueobject.Option;
import refactor.course.implementation.domain.test.valueobject.QuestionContent;
import refactor.course.implementation.domain.test.Test;

import java.util.List;

public record TestUpdateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,

        @Size(max = Description.MAX_LENGTH) String description,

        @PositiveOrZero int orderIndex,

        @NotNull @Size(min = Test.MIN_QUESTION_COUNT, max = Test.MAX_QUESTION_COUNT) @Valid
        List<@NotNull QuestionCommand> questions) {

    public record QuestionCommand(
            @NotBlank @Size(max = QuestionContent.MAX_QUESTION_LENGTH)
            String question,

            @PositiveOrZero int orderIndex,

            @NotNull @Size(min = Question.MIN_OPTION_COUNT, max = Question.MAX_OPTION_COUNT) @Valid
            List<@NotNull OptionCommand> options) {}

    public record OptionCommand(
            @NotBlank @Size(max = Option.MAX_LENGTH) String option, boolean isCorrect) {}
}
