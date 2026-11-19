package kira.progress.adapter.out.persistence.testattempt;

import common.domain.entity.Id;
import java.util.List;
import java.util.Optional;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptLoadPort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptRemovePort;
import kira.progress.application.port.out.persistance.testattempt.TestAttemptSavePort;
import kira.progress.domain.markers.Test;
import kira.progress.domain.markers.User;
import kira.progress.domain.testprogress.TestAttempt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class TestAttemptPersistenceAdapter implements TestAttemptLoadPort, TestAttemptSavePort, TestAttemptRemovePort {
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

    @Override
    public List<Id<User>> removeAllByTestId(Id<Test> testId) {
        List<Long> ids = repository.removeAllByTestIdAndReturnUserIds(testId.value());
        return ids.stream().map(Id::<User>of).toList();
    }
}
