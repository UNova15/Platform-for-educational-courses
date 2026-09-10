package refactor.common.exception.access;

import refactor.common.domain.Id;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.markers.Account;

public class LessonAccessException extends AccessException {

    public LessonAccessException(Id<Lesson> lessonId, Id<Account> requesterId) {
        super(lessonId.value(), requesterId.value());
    }
}
