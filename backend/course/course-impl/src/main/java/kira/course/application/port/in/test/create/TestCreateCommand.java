package kira.course.application.port.in.test.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import kira.course.application.port.in.test.shared.QuestionCommand;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.test.Test;

public record TestCreateCommand(
        @NotBlank @Size(max = Title.MAX_LENGTH) String title,

        @Size(max = Description.MAX_LENGTH) String description,

        @PositiveOrZero int orderIndex,

        @NotNull @Size(min = Test.MIN_QUESTION_COUNT, max = Test.MAX_QUESTION_COUNT) @Valid
        List<@NotNull QuestionCommand> questions) {}
