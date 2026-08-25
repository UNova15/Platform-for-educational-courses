package refactor.course.application.port.out.persistance.test;

import refactor.common.domain.Id;
import refactor.course.domain.internal.test.Test;

import java.util.Optional;

public interface TestLoadPort {
    Optional<Test> loadById(Id<Test> testId);

    boolean isExist(Id<Test> testId);
}
