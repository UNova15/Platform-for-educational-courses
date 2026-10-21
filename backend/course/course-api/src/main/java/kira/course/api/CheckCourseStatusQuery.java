package kira.course.api;

import java.util.Optional;

public interface CheckCourseStatusQuery {
    Optional<Boolean> isTestPublished(long testId);

    Optional<Boolean> isCoursePublished(long courseId);

    Optional<Boolean> isLessonPublished(long lessonId);

    Optional<Boolean> isModulePublished(long moduleId);
}
