package kira.course.application.port.in.lesson.update;

import common.domain.Id;
import common.domain.Result;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.markers.User;
import org.springframework.validation.annotation.Validated;

@Validated
public interface LessonUpdateUseCase {
    enum UpdateError{
        ACCESS_DENY,
        LESSON_NOT_FOUND,
        COURSE_ARCHIVED,
    }

    Result<Void,UpdateError> updateLesson(
            @Valid LessonUpdateCommand updateCommand, @NotNull Id<Lesson> lessonId, @NotNull Id<User> teacherId);
}
