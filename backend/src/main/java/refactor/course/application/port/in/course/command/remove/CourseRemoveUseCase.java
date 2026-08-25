package refactor.course.application.port.in.course.command.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.internal.course.Course;
import refactor.course.domain.external.User;

@Validated
public interface CourseRemoveUseCase {
    void removeCourse(@NotNull Id<User> teacherId, @NotNull Id<Course> courseId);
}
