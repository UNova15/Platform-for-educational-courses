package progress.application.port.in.enrollment;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.User;

@Validated
public interface UserEnrollmentUseCase {
    void enrollToCourse(@NotNull Id<User> userId, @NotNull Id<Course> courseId);
}
