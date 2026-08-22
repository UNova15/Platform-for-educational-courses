package refactor.course.application.port.in.lesson.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonCreateUseCase {
    LessonCreateResult createLesson(
            @Valid LessonCreateCommand createCommand, @PositiveOrZero long teacherId, @PositiveOrZero long moduleId);
}
