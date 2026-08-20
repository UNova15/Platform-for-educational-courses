package refactor.course.application.port.in.course.command.create;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseCreateUseCase {

    CourseCreateResult createCourseWithContent(@Valid CourseCreateCommand command);
}
