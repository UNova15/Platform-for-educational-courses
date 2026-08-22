package refactor.course.application.port.in.course.command.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseUpdateUseCase {
    void updateCourse(
            @Valid CourseUpdateCommand updateCommand, @PositiveOrZero long courseId, @PositiveOrZero long userId);
}
