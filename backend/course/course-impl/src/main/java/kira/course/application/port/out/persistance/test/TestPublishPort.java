package kira.course.application.port.out.persistance.test;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;

public interface TestPublishPort {
    void publishAllInCourse(Id<Course> courseId);

    void publishAllInModule(Id<CourseModule> moduleId);
}
