package kira.course.adapter.out.persistance.course;

import common.domain.Id;
import kira.course.domain.course.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import kira.course.application.port.out.persistance.course.CourseLoadPort;
import kira.course.application.port.out.persistance.course.CourseRemovePort;
import kira.course.application.port.out.persistance.course.CourseSavePort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CoursePersistenceAdapter implements CourseLoadPort, CourseSavePort, CourseRemovePort {
    private final DataCourseRepository courseRepository;
    private final CourseMapper mapper;

    @Override
    public boolean isExist(Id<Course> id) {
        return courseRepository.existsById(id.value());
    }

    @Override
    public Optional<Course> loadById(Id<Course> id) {
        Optional<CourseEntity> entity = courseRepository.findById(id.value());

        if (entity.isEmpty()) return Optional.empty();

        return entity.map(mapper::toDomain);
    }

    @Override
    public void remove(Course course) {
        courseRepository.deleteById(course.id().value());
    }

    @Override
    public Course save(Course course) {
        CourseEntity entity = mapper.toEntity(course);
        CourseEntity saved = courseRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
