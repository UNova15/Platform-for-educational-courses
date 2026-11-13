package kira.course.application.port.out.persistance.lesson;

import kira.course.domain.lesson.Lesson;

public interface LessonSavePort {
    Lesson save(Lesson lesson);
}
