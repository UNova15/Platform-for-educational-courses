package refactor.course.application.port.out.persistance.module;

import refactor.course.domain.module.CourseModule;

import java.util.List;

public interface ModuleSavePort {

    CourseModule save(CourseModule module);

    List<CourseModule> saveAll(List<CourseModule> modules);
}
