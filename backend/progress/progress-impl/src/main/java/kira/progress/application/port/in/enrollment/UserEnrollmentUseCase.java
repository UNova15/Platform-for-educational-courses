package kira.progress.application.port.in.enrollment;

import common.domain.Id;
import jakarta.validation.constraints.NotNull;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface UserEnrollmentUseCase {
    void enrollToCourse(@NotNull Id<User> userId, @NotNull Id<Course> courseId);
}
