package refactor.course.application.port.out.persistance.module;

import refactor.course.domain.module.CourseModule;

public interface ModuleRemovePort {
    void remove(CourseModule module);

    void removeById(long moduleId);
}
