package refactor.course.application.port.in.test.command.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import refactor.course.domain.question.Question;
import refactor.course.domain.question.QuestionTitle;

import java.util.List;

public record QuestionUpdateCommand(
        @NotBlank @Size(max = QuestionTitle.MAX_QUESTION_LENGTH)
        String question,

        @PositiveOrZero int orderIndex,
        @NotNull @Size(max = Question.MAX_OPTION_COUNT) List<@NotNull AnswerOptionUpdateCommand> options) {}
