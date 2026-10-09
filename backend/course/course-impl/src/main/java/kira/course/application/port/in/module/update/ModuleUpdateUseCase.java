package kira.course.application.port.in.module.update;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleUpdateUseCase {
    enum UpdateError {
        MODULE_NOT_FOUND,
        ACCESS_DENY,
        COURSE_ARCHIVED
    }

    Result<Void, UpdateError> updateModule(
            @Valid ModuleUpdateCommand updateCommand, @NotNull Id<CourseModule> moduleId, @NotNull Id<User> teacherId);
}
