package refactor.course.application.port.in.course.command.create;

import jakarta.validation.constraints.*;
import refactor.common.exception.domain.OrderException;
import refactor.common.util.OrderValidator;

import java.util.List;

import static refactor.course.domain.module.CourseModule.MAX_LESSONS_COUNT;
import static refactor.course.domain.module.CourseModule.MAX_TESTS_COUNT;
import static refactor.course.domain.module.ModuleDescription.MAX_MODULE_DESCRIPTION_SIZE;
import static refactor.course.domain.module.ModuleTitle.MAX_MODULE_TITLE_SIZE;

public record CourseBulkModuleCommand(
        @NotBlank @Size(max = MAX_MODULE_TITLE_SIZE) String title,
        @Size(max = MAX_MODULE_DESCRIPTION_SIZE) String description,
        @Positive int orderIndex,
        @NotEmpty @Size(max = MAX_LESSONS_COUNT) List<@NotNull CourseBulkLessonCommand> lessons,
        @NotNull @Size(max = MAX_TESTS_COUNT) List<@NotNull CourseBulkTestCommand> tests) {

    public CourseBulkModuleCommand {
        if (!OrderValidator.isValidSequence(lessons, CourseBulkLessonCommand::orderIndex)) {
            throw new OrderException("Invalid order in lessons");
        }

        if (!OrderValidator.isValidSequence(tests, CourseBulkTestCommand::orderIndex)) {
            throw new OrderException("Invalid order in test");
        }
    }
}
