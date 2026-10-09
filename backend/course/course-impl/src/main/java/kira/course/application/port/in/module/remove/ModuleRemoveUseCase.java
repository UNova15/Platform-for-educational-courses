package kira.course.application.port.in.module.remove;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleRemoveUseCase {
    enum RemoveError{
        MODULE_NOT_FOUND,
        ACCESS_DENY,
        COURSE_ARCHIVED,
        MODULE_NOT_EMPTY
    }

    Result<Void,RemoveError> removeModule(@NotNull Id<CourseModule> moduleId, @NotNull Id<User> teacherId);
}
