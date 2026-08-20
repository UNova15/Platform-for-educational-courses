package refactor.course.application.port.out.persistance.test;

import refactor.course.application.port.in.test.query.TestQueryResult;

public interface TestQueryPort {
    TestQueryResult findTest(long testId);
}
