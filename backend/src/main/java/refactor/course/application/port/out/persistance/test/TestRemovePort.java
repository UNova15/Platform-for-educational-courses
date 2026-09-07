package refactor.course.application.port.out.persistance.test;

import refactor.common.domain.Id;
import refactor.course.domain.test.Test;

public interface TestRemovePort {
    void removeTestById(Id<Test> testId);
}
