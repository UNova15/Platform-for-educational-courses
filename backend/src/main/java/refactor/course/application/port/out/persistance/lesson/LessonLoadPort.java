package refactor.course.application.port.out.persistance.lesson;

import refactor.course.domain.lesson.Lesson;

import java.util.Optional;

public interface LessonLoadPort {
    Optional<Lesson> loadLessonById(long lessonId);

    boolean isExist(long lessonId);
}
