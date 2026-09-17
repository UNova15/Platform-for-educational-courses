package kira.course.application.service.api;

import common.domain.Id;
import kira.course.api.CourseAnswerKey;
import kira.course.api.TestAnswerKeyQuery;
import kira.course.application.port.out.persistance.query.TestAnswerKeyQueryPort;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestAnswerQueryHandler implements TestAnswerKeyQuery {
    private final TestAnswerKeyQueryPort queryPort;

    @Override
    public Optional<CourseAnswerKey> findAnswerKeyByTestId(long testId) {
        Id<Test> mappedTestId = Id.of(testId);

        return queryPort.findAnswerKeyByTestId(mappedTestId);
    }
}
