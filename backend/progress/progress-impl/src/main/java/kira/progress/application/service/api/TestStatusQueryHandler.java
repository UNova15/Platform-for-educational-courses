package kira.progress.application.service.api;

import common.domain.Id;
import kira.progress.api.TestStatusQuery;
import kira.progress.application.port.out.persistance.query.TestAttemptStatusQueryPort;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestStatusQueryHandler implements TestStatusQuery {
    private final TestAttemptStatusQueryPort testStatusPort;

    @Override
    public boolean hasCompletedTest(long studentId, long testId) {
        Id<User> userId = Id.of(studentId);
        Id<Test> courseTestId = Id.of(testId);

        return testStatusPort.isTestCompleted(userId, courseTestId);
    }

    @Override
    public boolean isSolvingTest(long studentId, long testId) {
        Id<User> userId = Id.of(studentId);
        Id<Test> courseTestId = Id.of(testId);

        return testStatusPort.isStudentSolvingTest(userId, courseTestId);
    }
}
