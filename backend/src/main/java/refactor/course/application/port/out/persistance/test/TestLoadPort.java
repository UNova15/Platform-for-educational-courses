package refactor.course.application.port.out.persistance.test;

import refactor.course.domain.test.Test;

import java.util.Optional;

public interface TestLoadPort {
    Optional<Test> loadById(long testId);

    boolean isExist(long testId);
}
