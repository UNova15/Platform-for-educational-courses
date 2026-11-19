package kira.course.application.port.out.persistance.test;

import common.domain.entity.Id;
import kira.course.domain.test.Test;

public interface TestRemovePort {
    void removeTestById(Id<Test> testId);
}
