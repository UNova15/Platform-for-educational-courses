package refactor.course.application.port.in.course.command.remove;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseRemoveUseCase {
    void removeCourse(@Valid CourseRemoveCommand removeCommand);
}
