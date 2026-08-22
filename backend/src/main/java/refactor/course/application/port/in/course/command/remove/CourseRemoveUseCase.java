package refactor.course.application.port.in.course.command.remove;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseRemoveUseCase {
    void removeCourse(@PositiveOrZero long teacherId, @PositiveOrZero long courseId);
}
