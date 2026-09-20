package kira.progress.adapter.out.persistence.query.attemptstatus;

import common.domain.Id;
import kira.progress.application.port.out.persistance.query.TestAttemptStatusQueryPort;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestStatusPersistenceAdapter implements TestAttemptStatusQueryPort {
    private final TestStatusRepository repository;

    @Override
    public boolean isTestCompleted(Id<User> studentId, Id<Test> testId) {
        return repository.isTestCompleted(studentId.value(), testId.value());
    }

    @Override
    public boolean isStudentSolvingTest(Id<User> studentId, Id<Test> testId) {
        return repository.isStudentSolvingTest(studentId.value(), testId.value());
    }
}
