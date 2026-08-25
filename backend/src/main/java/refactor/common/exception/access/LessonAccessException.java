package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.internal.lesson.Lesson;
import refactor.course.domain.external.User;

public class LessonAccessException extends AccessException {

    public LessonAccessException(Id<Lesson> lessonId, Id<User> requesterId) {
        super(lessonId.value(), requesterId.value());
    }
}
