package kira.course.application.port.out.persistance.module;

import java.util.List;
import kira.course.domain.module.CourseModule;

public interface ModuleSavePort {

    CourseModule save(CourseModule module);

    List<CourseModule> saveAll(List<CourseModule> modules);
}
