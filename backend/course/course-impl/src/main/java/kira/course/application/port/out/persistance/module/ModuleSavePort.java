package kira.course.application.port.out.persistance.module;

import kira.course.domain.module.CourseModule;

public interface ModuleSavePort {
    CourseModule save(CourseModule module);
}
