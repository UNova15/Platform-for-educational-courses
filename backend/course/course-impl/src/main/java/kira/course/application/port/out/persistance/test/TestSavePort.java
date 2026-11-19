package kira.course.application.port.out.persistance.test;

import kira.course.domain.test.Test;

public interface TestSavePort {
    Test save(Test test);
}
