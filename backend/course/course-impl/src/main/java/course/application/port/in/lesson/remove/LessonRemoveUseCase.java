package course.application.port.in.lesson.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.markers.Account;

@Validated
public interface LessonRemoveUseCase {
    void removeLesson(@NotNull Id<Account> teacherId, @NotNull Id<Lesson> lessonId);
}
