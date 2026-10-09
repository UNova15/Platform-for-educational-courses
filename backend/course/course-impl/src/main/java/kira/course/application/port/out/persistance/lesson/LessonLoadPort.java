package kira.course.application.port.out.persistance.lesson;

import common.domain.Id;
import java.util.Optional;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;

public interface LessonLoadPort {
    Optional<Lesson> loadLessonById(Id<Lesson> lessonId);

    boolean isExistsInModule(Id<CourseModule> moduleId);
}
