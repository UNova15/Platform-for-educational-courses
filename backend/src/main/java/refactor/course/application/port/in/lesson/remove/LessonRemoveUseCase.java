package refactor.course.application.port.in.lesson.remove;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonRemoveUseCase {
    void removeLesson(@PositiveOrZero long teacherId, @PositiveOrZero long lessonId);
}
