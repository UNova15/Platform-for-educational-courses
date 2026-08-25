package refactor.course.application.port.in.course.command.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.external.User;

@Validated
public interface CourseCreateUseCase {

    CourseCreateResult createCourseWithContent(@Valid CourseCreateCommand command, @NotNull Id<User> userId);
}
