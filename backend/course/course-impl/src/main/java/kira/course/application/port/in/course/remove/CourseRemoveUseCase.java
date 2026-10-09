package kira.course.application.port.in.course.remove;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.course.Course;
import kira.course.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseRemoveUseCase {
    enum RemoveError {
        COURSE_NOT_FOUND,
        ACCESS_DENIED,
        COURSE_IS_NOT_IN_DRAFT
    }

    Result<Void, RemoveError> removeCourse(@NotNull Id<User> teacherId, @NotNull Id<Course> courseId);
}
