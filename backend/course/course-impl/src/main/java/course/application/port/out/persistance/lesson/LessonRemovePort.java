package course.application.port.out.persistance.lesson;

import refactor.common.domain.Id;
import refactor.course.implementation.domain.lesson.Lesson;

public interface LessonRemovePort {
    void removeById(Id<Lesson> lessonId);
}
