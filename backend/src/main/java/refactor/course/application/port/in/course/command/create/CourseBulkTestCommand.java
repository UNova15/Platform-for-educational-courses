package refactor.course.application.port.in.course.command.create;

import jakarta.validation.constraints.*;
import refactor.common.exception.domain.OrderException;
import refactor.common.util.OrderValidator;

import java.util.List;

import static refactor.course.domain.test.Test.MAX_QUESTION_COUNT;

public record CourseBulkTestCommand(
        @Positive int orderIndex,
        @NotBlank String title,
        String description,
        @NotEmpty @Size(max = MAX_QUESTION_COUNT) List<@NotNull CourseBulkQuestionCommand> questions) {

    public CourseBulkTestCommand {
        if (!OrderValidator.isValidSequence(questions, CourseBulkQuestionCommand::orderIndex)) {
            throw new OrderException("Invalid questions order");
        }
    }
}
