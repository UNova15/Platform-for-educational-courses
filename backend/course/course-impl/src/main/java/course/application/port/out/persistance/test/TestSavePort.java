package course.application.port.out.persistance.test;

import refactor.course.implementation.domain.test.Test;

import java.util.List;

public interface TestSavePort {
    List<Test> saveAll(List<Test> tests);

    Test save(Test test);
}
