package kira.course.application.port.in.test.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import kira.course.domain.test.valueobject.Option;

// Общая структура ответа для команд создания и изменения теста. При изменении вынести в отдельные классы
public record OptionCommand(
        @NotBlank @Size(max = Option.MAX_LENGTH) String option, boolean isCorrect) {}
