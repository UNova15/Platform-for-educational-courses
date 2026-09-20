package kira.progress.adapter.out.persistence.testattempt;

import common.domain.Id;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptLoadPort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptSavePort;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAttempt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TestAttemptPersistenceAdapter implements TestAttemptLoadPort, TestAttemptSavePort {
    private final DataTestAttemptRepository repository;
    private final TestAttemptMapper mapper;

    @Override
    public Optional<TestAttempt> loadByUserIdAndTestId(Id<User> userId, Id<Test> testId) {
        return repository.findByTestIdAndUserId(testId.value(), userId.value()).map(mapper::toDomain);
    }

    @Override
    public boolean isAttemptExist(Id<User> studentId, Id<Test> testId) {
        return repository.existsByUserIdAndTestId(studentId.value(), testId.value());
    }

    @Override
    public void save(TestAttempt attempt) {
        TestAttemptEntity entity = mapper.toEntity(attempt);
        repository.save(entity);
    }
}
