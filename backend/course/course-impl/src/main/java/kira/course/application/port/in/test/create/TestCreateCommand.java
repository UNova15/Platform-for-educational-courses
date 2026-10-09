package kira.course.application.port.in.test.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.test.Question;
import kira.course.domain.test.Test;
import kira.course.domain.test.valueobject.Option;
import kira.course.domain.test.valueobject.QuestionContent;

public record TestCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,

        @Size(max = Description.MAX_LENGTH) String description,

        @PositiveOrZero int orderIndex,

        @NotNull @Size(min = Test.MIN_QUESTION_COUNT, max = Test.MAX_QUESTION_COUNT) @Valid
        List<@NotNull QuestionCommand> questions) {

    public record QuestionCommand(
            @NotBlank @Size(max = QuestionContent.MAX_LENGTH)
            String question,

            @PositiveOrZero int orderIndex,

            @NotNull @Size(min = Question.MIN_OPTION_COUNT, max = Question.MAX_OPTION_COUNT) @Valid
            List<@NotNull OptionCommand> options) {}

    public record OptionCommand(
            @NotBlank @Size(max = Option.MAX_LENGTH) String option, boolean isCorrect) {}
}
