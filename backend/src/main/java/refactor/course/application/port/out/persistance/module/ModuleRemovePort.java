package refactor.course.application.port.out.persistance.module;

import refactor.common.domain.Id;
import refactor.course.domain.internal.module.CourseModule;

public interface ModuleRemovePort {
    void remove(CourseModule module);

    void removeById(Id<CourseModule> moduleId);
}
