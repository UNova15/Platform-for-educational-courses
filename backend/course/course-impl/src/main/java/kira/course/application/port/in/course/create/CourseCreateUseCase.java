package kira.course.application.port.in.course.create;

import common.domain.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseCreateUseCase {

    CourseCreateResult createCourseWithContent(@Valid CourseCreateCommand command, @NotNull Id<User> userId);
}
