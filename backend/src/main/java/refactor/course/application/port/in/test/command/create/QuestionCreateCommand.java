package refactor.course.application.port.in.test.command.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.question.QuestionTitle;

import java.util.List;

public record QuestionCreateCommand(
        @NotBlank @Size(max = QuestionTitle.MAX_QUESTION_LENGTH)
        String question,

        @PositiveOrZero int orderIndex,
        @NotNull List<@NotNull AnswerOptionCreateCommand> options) {}
