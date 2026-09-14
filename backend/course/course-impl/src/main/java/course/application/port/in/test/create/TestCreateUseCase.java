package course.application.port.in.test.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.markers.Account;
import refactor.course.implementation.domain.module.CourseModule;

@Validated
public interface TestCreateUseCase {
    TestCreateResult createTest(
            @Valid TestCreateCommand command, @NotNull Id<Account> teacherId, @NotNull Id<CourseModule> moduleId);
}
