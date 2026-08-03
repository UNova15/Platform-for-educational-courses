package org.platform.platformforeducationalcourses.persistance.repository.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.platform.platformforeducationalcourses.domain.course.Course;
import org.platform.platformforeducationalcourses.persistance.entity.course.CourseEntity;
import org.platform.platformforeducationalcourses.persistance.entity.reference.ModuleRef;
import org.springframework.stereotype.Component;

@Component
public class PersistCourseMapper {

    public Course fromEntity(CourseEntity entity) {
        Set<Long> modulesIds = entity.getModules().stream().map(ModuleRef::id).collect(Collectors.toSet());
        return Course.restore(
                entity.getId(),
                entity.getTeacherId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getTag(),
                entity.getCreatedAt(),
                modulesIds);
    }

    public CourseEntity toEntity(Course course) {
        return CourseEntity.fromCourse(course);
    }

    public List<Course> fromListEntity(List<CourseEntity> entities) {
        return entities.stream().map(this::fromEntity).toList();
    }
}
