package org.platform.platformforeducationalcourses.persistance.repository.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import refactor.course.domain.module.CourseModule;
import org.platform.platformforeducationalcourses.persistance.entity.course.ModuleEntity;
import org.platform.platformforeducationalcourses.persistance.entity.reference.LessonRef;
import org.platform.platformforeducationalcourses.persistance.entity.reference.TestRef;
import org.springframework.stereotype.Component;

@Component
public class PersistModuleMapper {

    public List<CourseModule> toListModules(Iterable<ModuleEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toModule)
                .toList();
    }

    public CourseModule toModule(ModuleEntity entity) {
        Set<Long> lessonsIds = entity.getLessons().stream().map(LessonRef::id).collect(Collectors.toSet());
        Set<Long> autoTestsIds = entity.getAutoTests().stream().map(TestRef::id).collect(Collectors.toSet());
        return CourseModule.restore(
                entity.getId(),
                entity.getCourseId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getOrderIndex(),
                lessonsIds,
                autoTestsIds);
    }

    public List<ModuleEntity> toEntity(List<CourseModule> module) {
        return module.stream().map(ModuleEntity::fromModule).toList();
    }
}
