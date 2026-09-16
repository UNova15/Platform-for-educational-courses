package kira.course.application.port.out.persistance.test;


import common.domain.Id;
import kira.course.domain.test.Test;

import java.util.Optional;

public interface TestLoadPort {
    Optional<Test> loadById(Id<Test> testId);

    boolean isExist(Id<Test> testId);
}
