package kira.course.application.port.out.persistance.test;


import kira.course.domain.test.Test;

import java.util.List;

public interface TestSavePort {
    List<Test> saveAll(List<Test> tests);

    Test save(Test test);
}
