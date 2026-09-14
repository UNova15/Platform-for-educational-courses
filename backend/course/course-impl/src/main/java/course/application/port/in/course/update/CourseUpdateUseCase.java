package course.application.port.in.course.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.markers.Account;

@Validated
public interface CourseUpdateUseCase {
    void updateCourse(
            @Valid CourseUpdateCommand updateCommand, @NotNull Id<Course> courseId, @NotNull Id<Account> userId);
}
