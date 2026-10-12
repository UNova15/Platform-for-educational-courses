package kira.course.adapter.out.persistence.module;

import common.domain.Id;
import kira.course.domain.shared.Description;
import kira.course.domain.shared.Title;
import kira.course.domain.module.CourseModule;
import org.springframework.stereotype.Component;

@Component
class ModuleMapper {
    public CourseModule toDomain(ModuleEntity entity) {
        return CourseModule.restore(
                Id.of(entity.id()),
                Id.of(entity.courseId()),
                Title.of(entity.title()),
                Description.of(entity.description()),
                entity.orderIndex(),
                entity.status());
    }

    public ModuleEntity toEntity(CourseModule module) {
        Long id = module.id() == null ? null : module.id().value();
        return new ModuleEntity(
                id,
                module.courseId().value(),
                module.title().value(),
                module.description().value(),
                module.orderIndex(),
                module.status());
    }
}
