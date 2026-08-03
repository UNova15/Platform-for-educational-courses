package org.platform.platformforeducationalcourses.persistance.repository.mapper;

import java.util.List;
import java.util.stream.StreamSupport;
import org.platform.platformforeducationalcourses.domain.course.Lesson;
import org.platform.platformforeducationalcourses.persistance.entity.course.LessonEntity;
import org.springframework.stereotype.Component;

@Component
public class PersistLessonMapper {

    public List<LessonEntity> toListLessonsEntities(List<Lesson> lessons) {
        return lessons.stream().map(LessonEntity::fromLesson).toList();
    }

    public List<Lesson> toListLessons(Iterable<LessonEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toLesson)
                .toList();
    }

    public Lesson toLesson(LessonEntity entity) {
        return Lesson.restore(
                entity.getId(),
                entity.getModuleId(),
                entity.getTitle(),
                entity.getType(),
                entity.getContent(),
                entity.getOrderIndex(),
                entity.isMandatory());
    }
}
