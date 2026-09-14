package course.application.port.out.persistance.module;

import refactor.common.domain.Id;
import refactor.course.implementation.domain.module.CourseModule;

public interface ModuleRemovePort {
    void removeById(Id<CourseModule> moduleId);
}
