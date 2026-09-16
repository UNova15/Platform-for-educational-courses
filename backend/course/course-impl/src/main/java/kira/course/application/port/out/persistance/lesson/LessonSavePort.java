package kira.course.application.port.out.persistance.lesson;

import kira.course.domain.lesson.Lesson;

import java.util.List;

public interface LessonSavePort {

    List<Lesson> saveAll(List<Lesson> lessons);

    Lesson save(Lesson lesson);
}
