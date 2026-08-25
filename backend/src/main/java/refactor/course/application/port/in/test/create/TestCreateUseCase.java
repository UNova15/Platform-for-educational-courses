package refactor.course.application.port.in.test.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.external.User;
import refactor.course.domain.internal.module.CourseModule;

@Validated
public interface TestCreateUseCase {
    TestCreateResult createTest(
            @Valid TestCreateCommand command, @NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);
}
