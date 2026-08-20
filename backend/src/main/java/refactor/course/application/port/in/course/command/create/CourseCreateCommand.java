package refactor.course.application.port.in.course.command.create;

import jakarta.validation.constraints.*;

import java.util.List;

import refactor.common.exception.domain.OrderException;
import refactor.common.util.OrderValidator;
import refactor.course.domain.course.Tag;

import static refactor.course.domain.course.Course.MAX_MODULES_COUNT;
import static refactor.course.domain.course.CourseDescription.MAX_COURSE_DESCRIPTION_SIZE;
import static refactor.course.domain.course.CourseTitle.MAX_COURSE_TITLE_SIZE;

public record CourseCreateCommand(
        @PositiveOrZero long userId,
        @NotBlank @Size(max = MAX_COURSE_TITLE_SIZE) String title,
        @Size(max = MAX_COURSE_DESCRIPTION_SIZE) String description,
        Tag tag,
        @NotEmpty @Size(max = MAX_MODULES_COUNT) List<@NotNull CourseBulkModuleCommand> modules) {

    public CourseCreateCommand {
        if (!OrderValidator.isValidSequence(modules, CourseBulkModuleCommand::orderIndex)) {
            throw new OrderException("Invalid order in modules");
        }
    }
}
