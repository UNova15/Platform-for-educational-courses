package course.application.port.out.persistance.lesson;

import refactor.common.domain.Id;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;

import java.util.Optional;

public interface LessonLoadPort {
    Optional<Lesson> loadLessonById(Id<Lesson> lessonId);

    boolean isExist(Id<Lesson> lessonId);

    int countByModuleId(Id<CourseModule> moduleId);
}
