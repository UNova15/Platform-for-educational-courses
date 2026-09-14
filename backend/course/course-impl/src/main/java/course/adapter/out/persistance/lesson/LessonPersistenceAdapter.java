package course.adapter.out.persistance.lesson;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import refactor.common.domain.Id;
import course.application.port.out.persistance.lesson.LessonLoadPort;
import course.application.port.out.persistance.lesson.LessonRemovePort;
import course.application.port.out.persistance.lesson.LessonSavePort;
import refactor.course.implementation.domain.lesson.Lesson;
import refactor.course.implementation.domain.module.CourseModule;

import java.util.List;
import java.util.Optional;

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
    public boolean isExist(Id<Lesson> lessonId) {
        return repository.existsById(lessonId.value());
    }

    @Override
    public int countByModuleId(Id<CourseModule> moduleId) {
        return repository.countByModuleId(moduleId.value());
    }

    @Override
    public void removeById(Id<Lesson> lessonId) {
        repository.deleteById(lessonId.value());
    }

    @Override
    public List<Lesson> saveAll(List<Lesson> lessons) {
        List<LessonEntity> entities = mapper.toEntity(lessons);
        Iterable<LessonEntity> saved = repository.saveAll(entities);
        return mapper.toLesson(saved);
    }

    @Override
    public Lesson save(Lesson lesson) {
        LessonEntity entity = mapper.toEntity(lesson);
        LessonEntity saved = repository.save(entity);
        return mapper.toLesson(saved);
    }
}
