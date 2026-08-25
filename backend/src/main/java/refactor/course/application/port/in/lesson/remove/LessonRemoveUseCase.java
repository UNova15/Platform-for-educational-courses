package refactor.course.application.port.in.lesson.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.internal.lesson.Lesson;
import refactor.course.domain.external.User;

@Validated
public interface LessonRemoveUseCase {
    void removeLesson(@NotNull Id<User> teacherId, @NotNull Id<Lesson> lessonId);
}
