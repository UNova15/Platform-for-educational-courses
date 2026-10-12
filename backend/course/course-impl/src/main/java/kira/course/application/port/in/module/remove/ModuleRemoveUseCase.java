package kira.course.application.port.in.module.remove;

import common.domain.Id;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleRemoveUseCase {

    void removeModule(@NotNull Id<CourseModule> moduleId, @NotNull Id<User> teacherId);
}
