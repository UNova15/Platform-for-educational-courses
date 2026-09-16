package kira.course.application.port.in.module.create;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleCreateUseCase {
    ModuleCreateResult createModule(
            @Valid ModuleCreateCommand createCommand, @NotNull Id<Course> courseId, @NotNull Id<User> teacherId);
}
