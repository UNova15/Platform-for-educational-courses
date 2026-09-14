package course.application.port.in.course.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.markers.Account;

@Validated
public interface CourseCreateUseCase {

    CourseCreateResult createCourseWithContent(@Valid CourseCreateCommand command, @NotNull Id<Account> userId);
}
