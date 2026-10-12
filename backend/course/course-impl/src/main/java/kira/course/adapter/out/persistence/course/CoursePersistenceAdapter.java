package kira.course.adapter.out.persistence.course;

import common.domain.Id;
import java.util.Optional;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseRemovePort;
import kira.course.application.port.out.persistance.course.CourseSavePort;
import kira.course.application.port.out.persistance.course.CourseStatusPort;
import kira.course.domain.course.Course;
import kira.course.domain.course.CourseStatus;
import kira.course.domain.lesson.Lesson;
import kira.course.domain.module.CourseModule;
import kira.course.domain.test.Test;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class CoursePersistenceAdapter implements CourseLoadPort, CourseSavePort, CourseRemovePort, CourseStatusPort {
    private final DataCourseRepository repository;
    private final CourseMapper mapper;

    @Override
    public boolean isExist(Id<Course> id) {
        return repository.existsById(id.value());
    }

    @Override
    public Optional<Course> loadById(Id<Course> id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public void remove(Course course) {
        repository.deleteById(course.id().value());
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = mapper.toEntity(course);
        CourseEntity saved = repository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public CourseStatus findCourseStatusByModuleId(Id<CourseModule> moduleId) {
        return repository.findCourseStatusByModuleId(moduleId.value());
    }

    @Override
    public CourseStatus findCourseStatusByLessonId(Id<Lesson> lessonId) {
        return repository.findCourseStatusByLessonId(lessonId.value());
    }

    @Override
    public CourseStatus findCourseStatusByTestId(Id<Test> testId) {
        return repository.findCourseStatusByTestId(testId.value());
    }
}
