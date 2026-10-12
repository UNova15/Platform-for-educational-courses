package kira.course.application.port.out.persistance.lesson;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Status;

public interface LessonChangeStatusPort {
    void updateAllStatusesByCourseId(Id<Course> courseId, Status status);

    void updateAllStatusesByModuleId(Id<CourseModule> moduleId, Status status);
}
