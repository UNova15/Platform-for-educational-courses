package kira.course.application.port.out.persistance.test;

import common.domain.Id;
import java.util.Optional;
import kira.course.domain.test.Test;

public interface TestLoadPort {
    Optional<Test> loadById(Id<Test> testId);

    boolean isExist(Id<Test> testId);
}
