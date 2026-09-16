package kira.course.application.port.in.course.update;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseUpdateUseCase {
    void updateCourse(
            @Valid CourseUpdateCommand updateCommand, @NotNull Id<Course> courseId, @NotNull Id<User> userId);
}
