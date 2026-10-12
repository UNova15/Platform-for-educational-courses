package kira.course.application.port.out.persistance.query;

import common.domain.Id;
import java.util.Optional;
import kira.course.api.CourseModuleStructure;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface CourseStructureQueryPort {
    Optional<Id<Course>> findCourseIdByTestId(Id<Test> testId);

    Optional<Id<Course>> findCourseIdByLessonId(Id<Lesson> lessonId);

    Optional<CourseModuleStructure> findStudentsModuleStructureById(Id<CourseModule> moduleId);
}
