package refactor.course.application.port.out.persistance.module;

import refactor.course.application.port.in.module.query.ModuleQueryResult;

public interface ModuleQueryPort {
    ModuleQueryResult findModuleById(long moduleId);
}
