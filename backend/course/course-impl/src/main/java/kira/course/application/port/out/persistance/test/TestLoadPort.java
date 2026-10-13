package kira.course.application.port.out.persistance.test;

import common.domain.entity.Id;
import java.util.Optional;

import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;

public interface TestLoadPort {
    Optional<Test> loadById(Id<Test> testId);

    boolean isExistsInModule(Id<CourseModule> moduleId);
}
