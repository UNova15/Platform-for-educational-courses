package refactor.course.application.port.in.lesson.query;

import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonQueryUseCase {
    LessonQueryResult findLesson(@PositiveOrZero long lessonId);
}
