package refactor.course.application.service.query;

import lombok.RequiredArgsConstructor;
import refactor.course.application.port.in.test.query.TestQueryResult;
import org.springframework.stereotype.Service;
import refactor.course.application.port.in.test.query.TestQueryUseCase;
import refactor.course.application.port.out.persistance.test.TestQueryPort;

@Service
@RequiredArgsConstructor
public class TestQueryService implements TestQueryUseCase {
    private final TestQueryPort queryPort;

    @Override
    public TestQueryResult findTest(long testId) {
        return queryPort.findTest(testId);
    }
}
