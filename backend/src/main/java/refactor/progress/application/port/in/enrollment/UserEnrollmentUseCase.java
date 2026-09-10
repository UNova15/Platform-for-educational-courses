package refactor.progress.application.port.in.enrollment;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.User;

@Validated
public interface UserEnrollmentUseCase {
    void enrollToCourse(@NotNull Id<User> userId, @NotNull Id<Course> courseId);
}
