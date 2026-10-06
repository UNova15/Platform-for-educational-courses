package kira.course.application.port.out.persistance.test;

import java.util.List;
import kira.course.domain.test.Test;

public interface TestSavePort {
    List<Test> saveAll(List<Test> tests);

    Test save(Test test);
}
