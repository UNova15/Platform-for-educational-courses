package kira.course.adapter.out.persistence.lesson;

import common.domain.entity.Id;
import kira.course.domain.shared.Title;
import kira.course.domain.lesson.LessonContent;
import kira.course.domain.lesson.Lesson;
import org.springframework.stereotype.Component;

@Component
class LessonMapper {
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

    public LessonEntity toEntity(Lesson lesson) {
        Long id = lesson.id() == null ? null : lesson.id().value();
        return new LessonEntity(
                id,
                lesson.moduleId().value(),
                lesson.title().value(),
                lesson.content().type(),
                lesson.content().value(),
                lesson.orderIndex(),
                lesson.mandatory(),
                lesson.status());
    }
}
