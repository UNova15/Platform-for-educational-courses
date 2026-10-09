package kira.course.application.port.in.lesson.remove;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonRemoveUseCase {
    enum RemoveErrors {
        LESSON_NOT_FOUND,
        ACCESS_DENY,
        COURSE_ARCHIVED
    }

    Result<Void, RemoveErrors> removeLesson(@NotNull Id<User> teacherId, @NotNull Id<Lesson> lessonId);
}
