package course.application.port.out.persistance.lesson;

import refactor.course.implementation.domain.lesson.Lesson;

import java.util.List;

public interface LessonSavePort {

    List<Lesson> saveAll(List<Lesson> lessons);

    Lesson save(Lesson lesson);
}
