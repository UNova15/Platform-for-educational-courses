package refactor.course.application.port.out.persistance.lesson;

import refactor.common.domain.Id;
import refactor.course.domain.internal.lesson.Lesson;

public interface LessonRemovePort {
    void removeById(Id<Lesson> lessonId);
}
