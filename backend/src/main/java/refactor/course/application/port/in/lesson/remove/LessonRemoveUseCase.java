package refactor.course.application.port.in.lesson.remove;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import refactor.common.domain.Id;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.user.Account;

@Validated
public interface LessonRemoveUseCase {
    void removeLesson(@NotNull Id<Account> teacherId, @NotNull Id<Lesson> lessonId);
}
