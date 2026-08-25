package refactor.course.application.port.in.course.command.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;

@Validated
public interface CourseUpdateUseCase {
    void updateCourse(
            @Valid CourseUpdateCommand updateCommand, @NotNull Id<Course> courseId, @NotNull Id<User> userId);
}
