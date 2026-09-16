package kira.course.application.port.out.persistance.lesson;


import common.domain.Id;
import kira.course.domain.lesson.Lesson;

public interface LessonRemovePort {
    void removeById(Id<Lesson> lessonId);
}
