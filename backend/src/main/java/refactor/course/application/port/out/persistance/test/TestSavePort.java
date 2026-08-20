package refactor.course.application.port.out.persistance.test;

import refactor.course.domain.test.Test;

import java.util.List;

public interface TestSavePort {
    List<Test> saveAll(Iterable<Test> tests);

    Test save(Test test);
}
