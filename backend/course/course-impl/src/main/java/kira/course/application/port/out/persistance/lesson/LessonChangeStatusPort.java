package kira.course.application.port.out.persistance.lesson;

import common.domain.entity.Id;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;

public interface LessonChangeStatusPort {
    void updateAllStatusesByCourseId(Id<Course> courseId, CourseContentStatus status);

    void updateAllStatusesByModuleId(Id<CourseModule> moduleId, CourseContentStatus status);
}
