package kira.course.application.port.out.persistance.module;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;

public interface ModuleLoadPort {
    Optional<CourseModule> loadById(Id<CourseModule> moduleId);

    boolean isExistsModule(Id<CourseModule> moduleId);

    boolean isExistsModulesInCourse(Id<Course> courseId);
}
