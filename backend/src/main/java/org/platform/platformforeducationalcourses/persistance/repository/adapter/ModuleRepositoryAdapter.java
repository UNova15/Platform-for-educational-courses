package org.platform.platformforeducationalcourses.persistance.repository.adapter;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.platform.platformforeducationalcourses.domain.course.CourseModule;
import org.platform.platformforeducationalcourses.domain.ports.persistance.ModuleRepository;
import org.platform.platformforeducationalcourses.persistance.entity.course.ModuleEntity;
import org.platform.platformforeducationalcourses.persistance.repository.mapper.PersistModuleMapper;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataModuleRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class ModuleRepositoryAdapter implements ModuleRepository {
    private final DataModuleRepository repository;
    private final PersistModuleMapper mapper;

    @Override
    public Optional<CourseModule> findModuleIfUserIsOwner(long userId, long courseId, long moduleId) {
        Optional<ModuleEntity> entity = repository.findModuleIfUserIsOwner(userId, courseId, moduleId);
        return entity.map(mapper::toModule);
    }

    @Override
    // TODO npe
    public List<CourseModule> findAllByCourseId(long courseId) {
        return null;
    }

    @Override
    public List<CourseModule> save(List<CourseModule> module) {
        List<ModuleEntity> entities = mapper.toEntity(module);
        Iterable<ModuleEntity> savedModule = repository.saveAll(entities);
        return mapper.toListModules(savedModule);
    }
}
