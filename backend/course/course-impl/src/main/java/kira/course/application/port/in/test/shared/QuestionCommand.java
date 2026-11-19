package kira.course.application.port.in.test.shared;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.util.List;
import kira.course.domain.test.Question;
import kira.course.domain.test.valueobject.QuestionContent;

// Общая структура вопроса для команд создания и изменения теста.При изменении вынести в отдельные классы
public record QuestionCommand(
        @NotBlank @Size(max = QuestionContent.MAX_LENGTH) String question,

        @PositiveOrZero int orderIndex,

        @NotNull @Size(min = Question.MIN_OPTION_COUNT, max = Question.MAX_OPTION_COUNT) @Valid
        List<@NotNull OptionCommand> options) {}
