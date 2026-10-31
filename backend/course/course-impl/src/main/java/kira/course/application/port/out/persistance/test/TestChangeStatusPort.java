package kira.course.application.port.out.persistance.test;

import common.domain.entity.Id;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;

public interface TestChangeStatusPort {
    void updateAllStatusesInCourse(Id<Course> courseId, CourseContentStatus status);

    void updateAllStatusesInModule(Id<CourseModule> moduleId, CourseContentStatus status);
}
