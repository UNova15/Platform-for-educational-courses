package kira.course.adapter.out.persistence.module;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.application.port.out.persistance.module.*;
import kira.course.domain.course.Course;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ModulePersistenceAdapter
        implements ModuleLoadPort, ModuleRemovePort, ModuleSavePort, ModuleChangeStatusPort, ModuleCheckStatusPort {
    private final DataModuleRepository repository;
    private final ModuleMapper mapper;

    @Override
    public Optional<CourseModule> loadById(Id<CourseModule> moduleId) {
        return repository.findById(moduleId.value()).map(mapper::toDomain);
    }

    @Override
    public boolean isExistsModule(Id<CourseModule> moduleId) {
        return repository.existsById(moduleId.value());
    }

    @Override
    public boolean isExistsModulesInCourse(Id<Course> courseId) {
        return repository.existsByCourseId(courseId.value());
    }

    @Override
    public void removeById(Id<CourseModule> moduleId) {
        repository.deleteById(moduleId.value());
    }

    @Override
    public CourseModule save(CourseModule module) {
        ModuleEntity entity = mapper.toEntity(module);
        ModuleEntity savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public void updateAllStatusesByCourseId(Id<Course> courseId, CourseContentStatus status) {
        repository.updateAllStatusesByCourseId(courseId.value(), status.name());
    }

    @Override
    public CourseContentStatus findStatusByLessonId(Id<Lesson> lessonId) {
        return repository.findStatusByLessonId(lessonId.value());
    }

    @Override
    public CourseContentStatus findStatusByTestId(Id<Test> testId) {
        return repository.findStatusByTestId(testId.value());
    }

    @Override
    public Optional<CourseContentStatus> findStatusById(Id<CourseModule> moduleId) {
        return repository.findStatusById(moduleId.value());
    }
}
