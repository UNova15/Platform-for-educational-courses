package refactor.course.application.port.out.persistance.module;

import refactor.course.domain.module.CourseModule;

import java.util.Optional;

public interface ModuleLoadPort {
    Optional<CourseModule> loadById(long moduleId);

    boolean isExistModule(long moduleId);
}
