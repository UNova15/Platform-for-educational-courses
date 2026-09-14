package org.platform.platformforeducationalcourses.domain.ports.persistance;

import java.util.List;
import refactor.course.implementation.domain.test.Test;

public interface TestRepository {

    List<Test> save(List<Test> tests);

    List<Test> findAllByModuleIdIn(List<Long> ids);
}
