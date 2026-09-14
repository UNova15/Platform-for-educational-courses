package course.application.port.in.module.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.module.CourseModule;
import refactor.course.implementation.domain.markers.Account;

@Validated
public interface ModuleUpdateUseCase {
    void updateModule(
            @Valid ModuleUpdateCommand updateCommand, @NotNull Id<CourseModule> moduleId, @NotNull Id<Account> teacherId);
}
