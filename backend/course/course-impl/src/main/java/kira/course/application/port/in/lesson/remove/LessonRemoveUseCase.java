package kira.course.application.port.in.lesson.remove;

import common.domain.Id;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonRemoveUseCase {
    void removeLesson(@NotNull Id<User> teacherId, @NotNull Id<Lesson> lessonId);
}
