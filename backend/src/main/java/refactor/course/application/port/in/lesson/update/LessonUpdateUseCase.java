package refactor.course.application.port.in.lesson.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.internal.lesson.Lesson;
import refactor.course.domain.external.User;

@Validated
public interface LessonUpdateUseCase {
    void updateLesson(
            @Valid LessonUpdateCommand updateCommand, @NotNull Id<Lesson> lessonId, @NotNull Id<User> teacherId);
}
