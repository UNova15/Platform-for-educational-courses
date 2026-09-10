package refactor.course.application.port.in.module.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.markers.Account;

@Validated
public interface ModuleCreateUseCase {
    ModuleCreateResult createModule(
            @Valid ModuleCreateCommand createCommand, @NotNull Id<Course> courseId, @NotNull Id<Account> teacherId);
}
