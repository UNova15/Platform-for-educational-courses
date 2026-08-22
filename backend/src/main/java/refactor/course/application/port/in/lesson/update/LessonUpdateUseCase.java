package refactor.course.application.port.in.lesson.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonUpdateUseCase {
    void updateLesson(
            @Valid LessonUpdateCommand updateCommand, @PositiveOrZero long lessonId, @PositiveOrZero long teacherId);
}
