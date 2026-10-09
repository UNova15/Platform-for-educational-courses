package kira.course.adapter.out.persistence.test;

import common.domain.Id;
import java.util.Optional;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestRemovePort;
import kira.course.application.port.out.persistance.test.TestSavePort;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TestPersistenceAdapter implements TestRemovePort, TestSavePort, TestLoadPort {
    private final DataTestRepository repository;
    private final TestMapper mapper;

    @Override
    public Optional<Test> loadById(Id<Test> testId) {
        return repository.findById(testId.value()).map(mapper::toDomain);
    }

    @Override
    public void removeTestById(Id<Test> testId) {
        repository.deleteById(testId.value());
    }

    @Override
    public Test save(Test test) {
        TestEntity entity = mapper.toEntity(test);
        TestEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }
}
