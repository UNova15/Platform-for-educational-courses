package refactor.course.application.port.in.course.command.create;

import jakarta.validation.constraints.*;

import java.util.List;

import static refactor.course.domain.question.Question.MAX_OPTION_COUNT;
import static refactor.course.domain.question.Question.MIN_OPTION_COUNT;
import static refactor.course.domain.question.QuestionTitle.MAX_QUESTION_LENGTH;

public record CourseBulkQuestionCommand(
        @NotBlank @Size(max = MAX_QUESTION_LENGTH) String question,
        @Positive int orderIndex,

        @NotEmpty @Size(min = MIN_OPTION_COUNT, max = MAX_OPTION_COUNT)
        List<@NotNull CourseBulkOptionCommand> options) {}
