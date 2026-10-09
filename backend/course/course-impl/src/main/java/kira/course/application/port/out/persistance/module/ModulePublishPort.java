package kira.course.application.port.out.persistance.module;

import common.domain.Id;
import kira.course.domain.course.Course;

public interface ModulePublishPort {
    void publishAllByCourseId(Id<Course> courseId);
}
