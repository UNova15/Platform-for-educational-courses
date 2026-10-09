package kira.course.application.port.in.module.create;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface ModuleCreateUseCase {
    enum CreateError{
        COURSE_ARCHIVED,
        COURSE_NOT_FOUND,
        ACCESS_DENY
    }

    Result<ModuleCreateResult,CreateError> createModule(
            @Valid ModuleCreateCommand createCommand, @NotNull Id<Course> courseId, @NotNull Id<User> teacherId);
}
