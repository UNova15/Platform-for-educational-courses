package kira.course.application.port.out.persistance.module;


import common.domain.Id;
import kira.course.domain.module.CourseModule;

import java.util.Optional;

public interface ModuleLoadPort {
    Optional<CourseModule> loadById(Id<CourseModule> moduleId);

    boolean isExistModule(Id<CourseModule> moduleId);
}
