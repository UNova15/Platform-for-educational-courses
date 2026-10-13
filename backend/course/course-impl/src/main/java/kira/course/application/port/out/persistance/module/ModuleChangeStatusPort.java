package kira.course.application.port.out.persistance.module;

import common.domain.entity.Id;
import kira.course.domain.course.Course;
import kira.course.domain.shared.CourseContentStatus;

public interface ModuleChangeStatusPort {
    void updateAllStatusesByCourseId(Id<Course> courseId, CourseContentStatus status);
}
