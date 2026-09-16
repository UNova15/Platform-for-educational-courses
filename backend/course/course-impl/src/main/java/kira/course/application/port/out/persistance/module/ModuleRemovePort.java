package kira.course.application.port.out.persistance.module;


import common.domain.Id;
import kira.course.domain.module.CourseModule;

public interface ModuleRemovePort {
    void removeById(Id<CourseModule> moduleId);
}
