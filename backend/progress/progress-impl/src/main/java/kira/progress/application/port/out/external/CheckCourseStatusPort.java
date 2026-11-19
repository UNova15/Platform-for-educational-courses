package kira.progress.application.port.out.external;

import common.domain.entity.Id;
import kira.progress.domain.markers.Course;
import kira.progress.domain.markers.CourseModule;
import kira.progress.domain.markers.Lesson;
import kira.progress.domain.markers.Test;

public interface CheckCourseStatusPort {
    boolean isTestPublished(Id<Test> testId);

    boolean isCoursePublished(Id<Course> courseId);

    boolean isLessonPublished(Id<Lesson> lessonId);

    boolean isModulePublished(Id<CourseModule> moduleId);
}
