package org.platform.platformforeducationalcourses.persistance.repository.mapper;

import java.util.List;
import java.util.stream.StreamSupport;
import refactor.course.domain.lesson.Lesson;
import refactor.course.adapter.out.persistance.lesson.LessonEntity;
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
