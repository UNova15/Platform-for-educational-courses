package refactor.course.application.port.out.persistance.module;

import refactor.common.domain.Id;
import refactor.course.domain.module.CourseModule;

import java.util.Optional;

public interface ModuleLoadPort {
    Optional<CourseModule> loadById(Id<CourseModule> moduleId);

    boolean isExistModule(Id<CourseModule> moduleId);
}
