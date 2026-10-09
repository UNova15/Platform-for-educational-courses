package kira.course.application.port.in.course.update;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseUpdateUseCase {
    enum UpdateError {
        COURSE_NOT_FOUND,
        ACCESS_DENIED,
        COURSE_IS_ARCHIVED
    }

    Result<Void, UpdateError> updateCourse(
            @Valid CourseUpdateCommand updateCommand, @NotNull Id<Course> courseId, @NotNull Id<User> userId);
}
