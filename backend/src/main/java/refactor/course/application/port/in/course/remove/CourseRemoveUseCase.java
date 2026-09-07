package refactor.course.application.port.in.course.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.course.Course;
import refactor.course.domain.user.Account;

@Validated
public interface CourseRemoveUseCase {
    void removeCourse(@NotNull Id<Account> teacherId, @NotNull Id<Course> courseId);
}
