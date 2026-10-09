package kira.course.application.port.in.test.create;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestCreateUseCase {
    enum CreateError {
        MODULE_NOT_FOUND,
        ACCESS_DENY,
        COURSE_ARCHIVED
    }

    Result<TestCreateResult, CreateError> createTest(
            @Valid TestCreateCommand command, @NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);
}
