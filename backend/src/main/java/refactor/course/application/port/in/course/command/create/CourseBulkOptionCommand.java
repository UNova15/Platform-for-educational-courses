package refactor.course.application.port.in.course.command.create;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import static refactor.course.domain.option.Option.MAX_OPTION_LENGTH;

public record CourseBulkOptionCommand(
        @NotBlank @Size(max = MAX_OPTION_LENGTH) String option, boolean isCorrect) {}
