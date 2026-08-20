package org.platform.platformforeducationalcourses.persistance.repository.adapter;

import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import refactor.course.domain.lesson.Lesson;
import org.platform.platformforeducationalcourses.domain.ports.persistance.LessonRepository;
import org.platform.platformforeducationalcourses.persistance.entity.course.LessonEntity;
import org.platform.platformforeducationalcourses.persistance.repository.mapper.PersistLessonMapper;
import org.platform.platformforeducationalcourses.persistance.repository.provader.DataLessonRepository;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LessonRepositoryAdapter implements LessonRepository {
    private final DataLessonRepository repository;
    private final PersistLessonMapper mapper;

    @Override
    public List<Lesson> saveAll(List<Lesson> lessons) {
        List<LessonEntity> entities = mapper.toListLessonsEntities(lessons);
        Iterable<LessonEntity> savedLessons = repository.saveAll(entities);
        return mapper.toListLessons(savedLessons);
    }

    @Override
    public List<Lesson> findAllByModuleIdIn(List<Long> ids) {
        List<LessonEntity> entities = repository.findAllByModuleIdIn(ids);
        return mapper.toListLessons(entities);
    }

    @Override
    public Lesson save(Lesson lesson) {
        LessonEntity entity = LessonEntity.fromLesson(lesson);
        LessonEntity savedEntity = repository.save(entity);
        return mapper.toLesson(savedEntity);
    }

    @Override
    public Optional<Lesson> findById(long id) {
        Optional<LessonEntity> entity = repository.findById(id);
        return entity.map(mapper::toLesson);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }
}
