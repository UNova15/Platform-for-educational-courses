package refactor.course.adapter.out.persistance.module;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.common.domain.Id;
import refactor.course.application.port.out.persistance.module.ModuleLoadPort;
import refactor.course.application.port.out.persistance.module.ModuleRemovePort;
import refactor.course.application.port.out.persistance.module.ModuleSavePort;
import refactor.course.domain.module.CourseModule;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ModulePersistenceAdapter implements ModuleLoadPort, ModuleRemovePort, ModuleSavePort {
    private final DataModuleRepository repository;
    private final ModuleMapper mapper;

    @Override
    public Optional<CourseModule> loadById(Id<CourseModule> moduleId) {
        return repository.findById(moduleId.value()).map(mapper::toDomain);
    }

    @Override
    public boolean isExistModule(Id<CourseModule> moduleId) {
        return repository.existsById(moduleId.value());
    }

    @Override
    public void removeById(Id<CourseModule> moduleId) {
        repository.deleteById(moduleId.value());
    }

    @Override
    public CourseModule save(CourseModule module) {
        ModuleEntity entity = mapper.toEntity(module);
        ModuleEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public List<CourseModule> saveAll(List<CourseModule> modules) {
        List<ModuleEntity> entities = mapper.toEntity(modules);
        Iterable<ModuleEntity> saved = repository.saveAll(entities);
        return mapper.toDomain(saved);
    }
}
