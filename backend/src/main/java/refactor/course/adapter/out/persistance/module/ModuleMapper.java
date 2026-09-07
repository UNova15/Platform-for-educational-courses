package refactor.course.adapter.out.persistance.module;

import org.springframework.stereotype.Component;
import refactor.common.domain.Id;
import refactor.course.domain.common.Description;
import refactor.course.domain.common.Title;
import refactor.course.domain.module.CourseModule;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class ModuleMapper {
    public CourseModule toDomain(ModuleEntity entity) {
        return CourseModule.restore(
                Id.of(entity.id()),
                Id.of(entity.courseId()),
                Title.of(entity.title()),
                Description.of(entity.description()),
                entity.orderIndex());
    }

    public List<CourseModule> toDomain(Iterable<ModuleEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toDomain)
                .toList();
    }

    public ModuleEntity toEntity(CourseModule module) {
        return new ModuleEntity(
                module.id().value(),
                module.courseId().value(),
                module.title().value(),
                module.description().value(),
                module.orderIndex());
    }

    public List<ModuleEntity> toEntity(List<CourseModule> modules) {
        return modules.stream().map(this::toEntity).toList();
    }
}
