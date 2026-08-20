package refactor.course.application.port.in.lesson.command.remove;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonRemoveUseCase {
    void removeLesson(@Valid LessonRemoveCommand command);
}
