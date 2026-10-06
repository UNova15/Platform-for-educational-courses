package kira.course.application.port.out.persistance.module;

import common.domain.Id;
import java.util.Optional;
import kira.course.domain.module.CourseModule;

public interface ModuleLoadPort {
    Optional<CourseModule> loadById(Id<CourseModule> moduleId);

    boolean isExistModule(Id<CourseModule> moduleId);
}
