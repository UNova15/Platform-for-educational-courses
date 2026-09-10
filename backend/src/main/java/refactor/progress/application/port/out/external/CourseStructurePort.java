package refactor.progress.application.port.out.external;

import refactor.common.domain.Id;
import refactor.progress.domain.markers.Course;
import refactor.progress.domain.markers.CourseModule;
import refactor.progress.domain.markers.Lesson;
import refactor.progress.domain.markers.Test;

import java.util.Optional;

public interface CourseStructurePort {
    Optional<Id<Course>> findCourseIdByTestId(Id<Test> testId);

    Optional<Id<Course>> findCourseIdByLessonId(Id<Lesson> lessonId);

    Optional<ModuleStructure> findModuleStructureById(Id<CourseModule> moduleId);

    boolean isCourseExist(Id<Course> courseId);
}
