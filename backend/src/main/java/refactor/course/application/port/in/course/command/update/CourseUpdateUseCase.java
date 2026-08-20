package refactor.course.application.port.in.course.command.update;


import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CourseUpdateUseCase {
    void updateCourse(@Valid CourseUpdateCommand updateCommand);

}
