package refactor.course.application.port.out.persistance.lesson;

import refactor.course.domain.internal.lesson.Lesson;

import java.util.List;

public interface LessonSavePort {

    List<Lesson> saveAll(Iterable<Lesson> lessons);

    Lesson save(Lesson lesson);
}
