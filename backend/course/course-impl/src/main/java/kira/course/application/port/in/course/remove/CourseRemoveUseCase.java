package kira.course.application.port.in.course.remove;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseRemoveUseCase {
    void removeCourse(@NotNull Id<User> teacherId, @NotNull Id<Course> courseId);
}
