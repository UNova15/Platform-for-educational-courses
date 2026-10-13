package kira.course.adapter.out.persistence.lesson;

import common.domain.entity.Id;

import java.util.Optional;
import kira.course.application.port.out.persistance.lesson.LessonLoadPort;
import kira.course.application.port.out.persistance.lesson.LessonChangeStatusPort;
import kira.course.application.port.out.persistance.lesson.LessonRemovePort;
import kira.course.application.port.out.persistance.lesson.LessonSavePort;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class LessonPersistenceAdapter implements LessonLoadPort, LessonSavePort, LessonRemovePort, LessonChangeStatusPort {
    private final DataLessonRepository repository;
    private final LessonMapper mapper;

    @Override
    public Optional<Lesson> loadLessonById(Id<Lesson> lessonId) {
        return repository.findById(lessonId.value()).map(mapper::toLesson);
    }

    @Override
    public boolean isExistsInModule(Id<CourseModule> moduleId) {
        return repository.existsByModuleId(moduleId.value());
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

    @Override
    public void updateAllStatusesByCourseId(Id<Course> courseId, CourseContentStatus status) {
        repository.updateAllStatusesByCourseId(courseId.value(), status.name());
    }

    @Override
    public void updateAllStatusesByModuleId(Id<CourseModule> moduleId, CourseContentStatus status) {
        repository.updateAllStatusesByModuleId(moduleId.value(), status.name());
    }
}
