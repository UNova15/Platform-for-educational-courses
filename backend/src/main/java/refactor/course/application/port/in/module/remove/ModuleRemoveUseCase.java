package refactor.course.application.port.in.module.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.internal.module.CourseModule;
import refactor.course.domain.external.User;

@Validated
public interface ModuleRemoveUseCase {
    void removeModule(@NotNull Id<CourseModule> moduleId, @NotNull Id<User> teacherId);
}
