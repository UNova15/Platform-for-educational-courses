package org.platform.platformforeducationalcourses.domain.ports.persistance;

import java.util.List;
import java.util.Optional;
import refactor.course.domain.internal.module.CourseModule;

public interface ModuleRepository {
    Optional<CourseModule> findModuleIfUserIsOwner(long userId, long courseId, long moduleId);

    List<CourseModule> findAllByCourseId(long courseId);

    List<CourseModule> save(List<CourseModule> module);
}
