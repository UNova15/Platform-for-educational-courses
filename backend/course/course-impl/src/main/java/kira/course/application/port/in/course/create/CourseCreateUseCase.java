package kira.course.application.port.in.course.create;

import common.domain.Id;
import kira.course.domain.markers.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseCreateUseCase {

    CourseCreateResult createCourseWithContent(@Valid CourseCreateCommand command, @NotNull Id<User> userId);
}
