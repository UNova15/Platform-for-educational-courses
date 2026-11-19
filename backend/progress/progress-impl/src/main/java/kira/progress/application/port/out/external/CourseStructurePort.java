package kira.progress.application.port.out.external;

import common.domain.entity.Id;
import java.util.Optional;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.CourseModule;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;

public interface CourseStructurePort {
    Optional<Id<Course>> findCourseIdByTestId(Id<Test> testId);

    Optional<Id<Course>> findCourseIdByLessonId(Id<Lesson> lessonId);

    Optional<ModuleStructure> findStudentsModuleStructureById(Id<CourseModule> moduleId);
}
