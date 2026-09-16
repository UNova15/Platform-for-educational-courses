package kira.course.application.port.out.persistance.lesson;


import common.domain.Id;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;

import java.util.Optional;

public interface LessonLoadPort {
    Optional<Lesson> loadLessonById(Id<Lesson> lessonId);

    boolean isExist(Id<Lesson> lessonId);

    int countByModuleId(Id<CourseModule> moduleId);
}
