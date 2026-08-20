package refactor.course.application.port.in.lesson.command.update;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonUpdateUseCase {
    void updateLesson(@Valid LessonUpdateCommand updateCommand);
}
