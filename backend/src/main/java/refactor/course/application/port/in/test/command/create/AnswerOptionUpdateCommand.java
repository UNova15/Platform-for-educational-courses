package refactor.course.application.port.in.test.command.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import refactor.course.domain.option.Option;

public record AnswerOptionUpdateCommand(
        @NotBlank @Size(max = Option.MAX_OPTION_LENGTH) String option, boolean isCorrect) {}
