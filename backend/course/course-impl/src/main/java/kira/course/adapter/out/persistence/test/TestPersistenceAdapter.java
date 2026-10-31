package kira.course.adapter.out.persistence.test;

import common.domain.entity.Id;
import java.util.Optional;
import kira.course.application.port.out.persistance.test.*;
import kira.course.domain.course.Course;
import kira.course.domain.module.CourseModule;
import kira.course.domain.shared.CourseContentStatus;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class TestPersistenceAdapter
        implements TestRemovePort, TestSavePort, TestLoadPort, TestChangeStatusPort, TestCheckStatusPort {
    private final DataTestRepository repository;
    private final TestMapper mapper;

    @Override
    public Optional<Test> loadById(Id<Test> testId) {
        return repository.findById(testId.value()).map(mapper::toDomain);
    }

    @Override
    public boolean isExistsInModule(Id<CourseModule> moduleId) {
        return repository.existsByModuleId(moduleId.value());
    }

    @Override
    public void removeTestById(Id<Test> testId) {
        repository.deleteById(testId.value());
    }

    @Override
    public Test save(Test test) {
        TestEntity entity = mapper.toEntity(test);
        TestEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public void updateAllStatusesInCourse(Id<Course> courseId, CourseContentStatus status) {
        repository.updateAllStatusesByCourseId(courseId.value(), status.name());
    }

    @Override
    public void updateAllStatusesInModule(Id<CourseModule> moduleId, CourseContentStatus status) {
        repository.updateAllStatusesByModuleId(moduleId.value(), status.name());
    }

    @Override
    public Optional<CourseContentStatus> findTestStatusById(Id<Test> testId) {
        return repository.findStatusById(testId.value());
    }
}
