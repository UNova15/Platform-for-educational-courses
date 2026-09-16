package kira.course.application.port.in.module.update;

import common.domain.Id;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleUpdateUseCase {
    void updateModule(
            @Valid ModuleUpdateCommand updateCommand, @NotNull Id<CourseModule> moduleId, @NotNull Id<User> teacherId);
}
