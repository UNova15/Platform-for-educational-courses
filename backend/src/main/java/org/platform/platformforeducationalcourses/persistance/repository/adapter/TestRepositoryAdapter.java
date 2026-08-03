package org.platform.platformforeducationalcourses.persistance.repository.adapter;

import java.util.List;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.Test;
import org.platform.platformforeducationalcourses.domain.ports.persistance.TestRepository;
import org.platform.platformforeducationalcourses.persistance.entity.course.TestEntity;
import org.platform.platformforeducationalcourses.persistance.repository.mapper.PersistTestMapper;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataTestRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class TestRepositoryAdapter implements TestRepository {
    private final DataTestRepository repository;
    private final PersistTestMapper mapper;

    @Override
    public List<Test> save(List<Test> tests) {
        List<TestEntity> entities = mapper.toListEntities(tests);
        Iterable<TestEntity> savedEntities = repository.saveAll(entities);
        return mapper.toListTests(savedEntities);
    }

    @Override
    public List<Test> findAllByModuleIdIn(List<Long> ids) {
        List<TestEntity> entities = repository.findAllByModuleIdIn(ids);
        return mapper.toListTests(entities);
    }
}
