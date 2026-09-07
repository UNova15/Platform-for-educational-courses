package refactor.course.application.port.out.persistance.lesson;

import refactor.common.domain.Id;
import refactor.course.domain.lesson.Lesson;
import refactor.course.domain.module.CourseModule;

import java.util.Optional;

public interface LessonLoadPort {
    Optional<Lesson> loadLessonById(Id<Lesson> lessonId);

    boolean isExist(Id<Lesson> lessonId);

    int countByModuleId(Id<CourseModule> moduleId);
}
