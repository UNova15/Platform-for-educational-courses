package kira.course.adapter.out.persistance.lesson;

import common.domain.Id;
import kira.course.domain.common.Title;
import kira.course.domain.lesson.Content;
import kira.course.domain.lesson.Lesson;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class LessonMapper {
    public Lesson toLesson(LessonEntity entity) {
        return Lesson.restore(
                Id.of(entity.id()),
                Id.of(entity.moduleId()),
                Title.of(entity.title()),
                Content.of(entity.type(), entity.content()),
                entity.orderIndex(),
                entity.mandatory());
    }

    public List<Lesson> toLesson(Iterable<LessonEntity> entities) {
        return StreamSupport.stream(entities.spliterator(), false)
                .map(this::toLesson)
                .toList();
    }

    public LessonEntity toEntity(Lesson lesson) {
        return new LessonEntity(
                lesson.id().value(),
                lesson.moduleId().value(),
                lesson.title().value(),
                lesson.content().type(),
                lesson.content().value(),
                lesson.orderIndex(),
                lesson.mandatory());
    }

    public List<LessonEntity> toEntity(List<Lesson> lessons) {
        return lessons.stream().map(this::toEntity).toList();
    }
}
