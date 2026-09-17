package kira.course.api;

import java.util.Optional;

public interface CourseStructureQuery {

    Optional<Long> findCourseIdByTestId(long testId);

    Optional<Long> findCourseIdByLessonId(long lessonId);

    Optional<CourseModuleStructure> findModuleStructureById(long moduleId);
}
