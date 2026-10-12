package kira.course.application.port.out.persistance.test;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.Status;

public interface TestChangeStatusPort {
    void updateAllStatusesInCourse(Id<Course> courseId, Status status);

    void updateAllStatusesInModule(Id<CourseModule> moduleId, Status status);
}
