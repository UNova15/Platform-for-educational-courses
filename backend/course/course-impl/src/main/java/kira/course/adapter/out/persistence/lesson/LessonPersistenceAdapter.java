package kira.course.adapter.out.persistence.lesson;

import common.domain.Id;
import java.util.List;
import java.util.Optional;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonRemovePort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LessonPersistenceAdapter implements LessonLoadPort, LessonSavePort, LessonRemovePort {
    private final DataLessonRepository repository;
    private final LessonMapper mapper;

    @Override
    public Optional<Lesson> loadLessonById(Id<Lesson> lessonId) {
        return repository.findById(lessonId.value()).map(mapper::toLesson);
    }

    @Override
    public void removeById(Id<Lesson> lessonId) {
        repository.deleteById(lessonId.value());
    }

    @Override
    public Lesson save(Lesson lesson) {
        LessonEntity entity = mapper.toEntity(lesson);
        LessonEntity saved = repository.save(entity);
        return mapper.toLesson(saved);
    }
}
