package kira.course.application.port.out.persistance.module;

import common.domain.Id;
import kira.course.domain.course.Course;
import kira.course.domain.shared.Status;

public interface ModuleChangeStatusPort {
    void updateAllStatusesByCourseId(Id<Course> courseId, Status status);
}
