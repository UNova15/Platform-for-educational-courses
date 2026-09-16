package kira.course.application.port.out.persistance.module;


import kira.course.domain.module.CourseModule;

import java.util.List;

public interface ModuleSavePort {

    CourseModule save(CourseModule module);

    List<CourseModule> saveAll(List<CourseModule> modules);
}
