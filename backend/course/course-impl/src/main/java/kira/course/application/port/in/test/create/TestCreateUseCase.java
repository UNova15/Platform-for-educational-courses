package kira.course.application.port.in.test.create;

import common.domain.Id;
import kira.course.domain.markers.User;
import kira.course.domain.module.CourseModule;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface TestCreateUseCase {
    TestCreateResult createTest(
            @Valid TestCreateCommand command, @NotNull Id<User> teacherId, @NotNull Id<CourseModule> moduleId);
}
