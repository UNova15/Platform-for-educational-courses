package course.adapter.out.persistance.test;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.common.domain.Id;
import course.application.port.out.persistance.test.TestLoadPort;
import course.application.port.out.persistance.test.TestRemovePort;
import course.application.port.out.persistance.test.TestSavePort;
import refactor.course.implementation.domain.test.Test;

import java.util.List;
import java.util.Optional;

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
    public boolean isExist(Id<Test> testId) {
        return repository.existsById(testId.value());
    }

    @Override
    public void removeTestById(Id<Test> testId) {
        repository.deleteById(testId.value());
    }

    @Override
    public List<Test> saveAll(List<Test> tests) {
        List<TestEntity> entities = mapper.toEntity(tests);
        Iterable<TestEntity> saved = repository.saveAll(entities);
        return mapper.toDomain(saved);
    }

    @Override
    public Test save(Test test) {
        TestEntity entity = mapper.toEntity(test);
        TestEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }
}
