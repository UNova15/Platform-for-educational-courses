package kira.course.application.port.in.lesson.update;

import common.domain.Id;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonUpdateUseCase {
    void updateLesson(
            @Valid LessonUpdateCommand updateCommand, @NotNull Id<Lesson> lessonId, @NotNull Id<User> teacherId);
}
