package refactor.course.application.port.in.lesson.command.create;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonCreateUseCase {
    LessonCreateResult createLesson(@Valid LessonCreateCommand createCommand);
}
