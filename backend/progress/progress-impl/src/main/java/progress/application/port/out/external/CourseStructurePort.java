package progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.implementation.domain.markers.Course;
import refactor.progress.implementation.domain.markers.CourseModule;
import refactor.progress.implementation.domain.markers.Lesson;
import refactor.progress.implementation.domain.markers.Test;

import java.util.Optional;

public interface CourseStructurePort {
    Optional<Id<Course>> findCourseIdByTestId(Id<Test> testId);

    Optional<Id<Course>> findCourseIdByLessonId(Id<Lesson> lessonId);

    Optional<ModuleStructure> findModuleStructureById(Id<CourseModule> moduleId);

    boolean isCourseExist(Id<Course> courseId);
}
