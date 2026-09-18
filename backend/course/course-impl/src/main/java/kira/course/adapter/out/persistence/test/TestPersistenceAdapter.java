package kira.course.adapter.out.persistence.test;

import common.domain.Id;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import kira.course.application.port.out.persistance.test.TestLoadPort;
import kira.course.application.port.out.persistance.test.TestRemovePort;
import kira.course.application.port.out.persistance.test.TestSavePort;

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
