package course.application.port.in.course.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.course.Course;
import refactor.course.implementation.domain.markers.Account;

@Validated
public interface CourseRemoveUseCase {
    void removeCourse(@NotNull Id<Account> teacherId, @NotNull Id<Course> courseId);
}
