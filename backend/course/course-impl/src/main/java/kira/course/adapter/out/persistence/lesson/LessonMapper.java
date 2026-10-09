package kira.course.adapter.out.persistence.lesson;

import common.domain.Id;
import java.util.List;
import java.util.stream.StreamSupport;
import kira.course.domain.shared.Title;
import kira.course.domain.lesson.LessonContent;
import kira.course.domain.lesson.Lesson;
import org.springframework.stereotype.Component;

@Component
public class LessonMapper {
    public Lesson toLesson(LessonEntity entity) {
        return Lesson.restore(
                Id.of(entity.id()),
                Id.of(entity.moduleId()),
                Title.of(entity.title()),
                LessonContent.of(entity.type(), entity.content()),
                entity.orderIndex(),
                entity.mandatory(),
                entity.status());
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
                lesson.mandatory(),
                lesson.status());
    }

    public List<LessonEntity> toEntity(List<Lesson> lessons) {
        return lessons.stream().map(this::toEntity).toList();
    }
}
